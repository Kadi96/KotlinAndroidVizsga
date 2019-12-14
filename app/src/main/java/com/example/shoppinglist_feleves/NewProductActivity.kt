package com.example.shoppinglist_feleves

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_new_product.*

class NewProductActivity : AppCompatActivity() {

    companion object {
        var EXTRA_ID = "com.example.shoppinglist_feleves.EXTRA_ID"
        var EXTRA_NAME = "com.example.shoppinglist_feleves.EXTRA_NAME"
        var EXTRA_COMMENT = "com.example.shoppinglist_feleves.EXTRA_COMMENT"
        var EXTRA_TYPE = "com.example.shoppinglist_feleves.EXTRA_TYPE"
        var productTypes = arrayOf(
            "Tejtermék", "Pékárú", "Hús", "Gyümölcs-Zöldség",
            "Italok", "Ruházat", "Fagyaszott", "Tartós élelmiszer", "Édesség", "Egyéb"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)

        var arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, productTypes)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var spinner: Spinner = findViewById(R.id.new_product_spinner)
        spinner.adapter = arrayAdapter

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Termék módosítás"
            new_product_name.setText(intent.getStringExtra(EXTRA_NAME))
            new_product_comment.setText(intent.getStringExtra(EXTRA_COMMENT))
            val arrayStrings = resources.getStringArray(R.array.categories)
            val index = arrayStrings.indexOf(intent.getStringExtra(EXTRA_TYPE))
            new_product_spinner.setSelection(index)
        } else {
            title = "Termék hozzáadás"
        }

        save_button.setOnClickListener {
            saveProduct()
        }
        cancel_button.setOnClickListener {
            this.finish()
        }
    }

    private fun saveProduct() {

        if (new_product_name.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Nem adott meg termék nevet", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_NAME, new_product_name.text.toString())
            putExtra(EXTRA_COMMENT, new_product_comment.text.toString())
            putExtra(EXTRA_TYPE, new_product_spinner.selectedItem.toString())
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
