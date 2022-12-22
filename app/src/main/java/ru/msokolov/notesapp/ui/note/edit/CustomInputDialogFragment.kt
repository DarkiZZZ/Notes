package ru.msokolov.notesapp.ui.note.edit

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.AndroidEntryPoint
import ru.msokolov.notesapp.R
import ru.msokolov.notesapp.databinding.TextItemInputBinding

/**
 * Определяем слушатель через лямбду
 */
typealias CustomInputDialogListener = (requestKey: String, text: String) -> Unit

@AndroidEntryPoint
class CustomInputDialogFragment : DialogFragment() {


    /**
     * Переменные text и requestKey, которые получаем из аргументов
     */
    private val text: String
        get() = requireArguments().getString(ARG_TEXT)!!

    private val requestKey: String
        get() = requireArguments().getString(ARG_REQUEST_KEY)!!


    /**
     * Функция для создания диалогового окна
     */
    @SuppressLint("DialogFragmentCallbacksDetector")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = TextItemInputBinding.inflate(layoutInflater)
        dialogBinding.itemInputEditText.setText(text)

        /**
         * Создаем диалоговое окно, определяем параметры
         */
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.note_setup)
            .setView(dialogBinding.root)
            .setPositiveButton(R.string.action_confirm, null)
            .create()

        /**
         * Запрашиваем фокус(затемнение основного фрагмента) и показываем клавиатуру
         */
        dialog.setOnShowListener {
            dialogBinding.itemInputEditText.requestFocus()
            showKeyboard(dialogBinding.itemInputEditText)

            /**
             * Слушаем нажатие BUTTON_POSITIVE и проверяем содержимое EditText на
             * isBlank().
             * Собираем результат и кладем в bundle. Закрываем диалоговое окно
             */
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val enteredText = dialogBinding.itemInputEditText.text.toString()
                if (enteredText.isBlank()) {
                    dialogBinding.itemInputEditText.error = getString(R.string.empty_value)
                    return@setOnClickListener
                }
                parentFragmentManager.setFragmentResult(requestKey, bundleOf(KEY_TEXT_RESPONSE to enteredText))
                dismiss()
            }
        }
        dialog.setOnDismissListener { hideKeyboard(dialogBinding.itemInputEditText) }

        return dialog
    }


    /*Функции для показа и скрытия клавиатуры*/
    private fun showKeyboard(view: View) {
        view.post {
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }


    /**
     * Запрашиваем менеджер ввода для
     * @param[view] - диалогового окна
     */
    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }


    /*Константы для SavedState и слушателя:*/
    companion object {
        @JvmStatic private val TAG = CustomInputDialogFragment::class.java.simpleName
        @JvmStatic private val KEY_TEXT_RESPONSE = "KEY_TEXT_RESPONSE"
        @JvmStatic private val ARG_TEXT = "ARG_TEXT"
        @JvmStatic private val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"


        /**
         * Функция для показа диалогового окна:
         * @param[text] - текст для показа в EditText
         * @param[requestKey] - ключ запроса для аргумента при передачи данных
         */
        fun show(manager: FragmentManager, text: String, requestKey: String) {
            val dialogFragment = CustomInputDialogFragment()
            dialogFragment.arguments = bundleOf(
                ARG_TEXT to text,
                ARG_REQUEST_KEY to requestKey
            )
            dialogFragment.show(manager, TAG)
        }


        /*Установка слушателя:*/
        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner,
                          requestKey: String, listener: CustomInputDialogListener) {
            manager.setFragmentResultListener(requestKey, lifecycleOwner, FragmentResultListener {
                    key, result ->
                listener.invoke(key, result.getString(KEY_TEXT_RESPONSE)!!)
            })
        }
    }
}