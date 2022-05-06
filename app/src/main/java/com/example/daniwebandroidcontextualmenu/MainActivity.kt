package com.example.daniwebandroidcontextualmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            menuInflater.inflate(R.menu.contextual, menu)
            return true
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return handleContextMenuItem(item)
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu) = false
        override fun onDestroyActionMode(mode: ActionMode) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.hello)

        //Contextual floating menu
        //registerForContextMenu(textView)

        //Contextual action menu
        textView.setOnLongClickListener {
            startActionMode(actionModeCallback)
            true
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.contextual, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        super.onContextItemSelected(item)
        return handleContextMenuItem(item)
    }

    private fun handleContextMenuItem(item: MenuItem): Boolean {
        val textView = findViewById<TextView>(R.id.hello)
        val text = textView.text

        return when(item.itemId){
            R.id.shuffle -> {
                textView.text = text
                    .asSequence()
                    .shuffled()
                    .joinToString(separator = "") {
                        "$it"
                    }
                true
            }
            R.id.reset -> {
                textView.text = getString(R.string.hello_world)
                true
            }
            R.id.insert_random -> {
                textView.text = StringBuilder(text)
                    .insert(
                        text.indices.random(),
                        alphabet.random()
                    )
                true
            }
            else -> false
        }
    }

    companion object {
        //Don't want chars 91->96
        private val alphabet = CharRange('A', 'Z').plus(CharRange('a', 'z'))
    }

}