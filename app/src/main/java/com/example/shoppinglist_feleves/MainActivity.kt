package com.example.shoppinglist_feleves

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_product.*
import kotlinx.android.synthetic.main.recyclerview_item.*

class MainActivity : AppCompatActivity() {

    companion object{
        var ADD_PRODUCT_REQUEST = 1
        var EDIT_PRODUCT_REQUEST = 2
    }

    private lateinit var productViewModel : ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        title = "Bevásárló lista"

        fab.setOnClickListener {
            startActivityForResult(
            Intent(this,NewProductActivity::class.java),
                ADD_PRODUCT_REQUEST
            )
        }


        var recyclerView : RecyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var productAdapter = ProductAdapter()
        recyclerView.adapter = productAdapter

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        productViewModel.getAll().observe(this, Observer<List<Product>> {
            productAdapter.submitList(it)
        })
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),ItemTouchHelper.LEFT){
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                viewHolder.adapterPosition
//            }
//        }
        productAdapter.setOnItemClickListener(object :IOnItemClickListener{
            override fun onItemTypeClick(type: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onItemClick(products: Product) {
                val intent = Intent(baseContext,NewProductActivity::class.java)
                intent.putExtra(NewProductActivity.EXTRA_ID,products.id)
                intent.putExtra(NewProductActivity.EXTRA_NAME,products.name)
                intent.putExtra(NewProductActivity.EXTRA_TYPE,products.itemType)
                intent.putExtra(NewProductActivity.EXTRA_COMMENT,products.comment)
                startActivityForResult(intent, EDIT_PRODUCT_REQUEST)
            }

            override fun onDeleteClick(products: Product) {
                productViewModel.delete(products)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == ADD_PRODUCT_REQUEST && resultCode == Activity.RESULT_OK){
            var name = data?.getStringExtra(NewProductActivity.EXTRA_NAME)
            var comment = data?.getStringExtra(NewProductActivity.EXTRA_COMMENT)
            var type = data?.getStringExtra(NewProductActivity.EXTRA_TYPE)

            var product = Product(name!!,type!!,comment!!)
            productViewModel.insert(product)
        } else if(requestCode == EDIT_PRODUCT_REQUEST && resultCode == Activity.RESULT_OK){
            val id = data?.getIntExtra(NewProductActivity.EXTRA_ID,-1)
            if(id == -1) {
                Toast.makeText(this,"Nem menthető",Toast.LENGTH_SHORT).show()
                return
            }
            var name = data?.getStringExtra(NewProductActivity.EXTRA_NAME)
            var comment = data?.getStringExtra(NewProductActivity.EXTRA_COMMENT)
            var type = data?.getStringExtra(NewProductActivity.EXTRA_TYPE)
            var product = Product(name!!,type!!,comment!!)
            product.id = data?.getIntExtra(NewProductActivity.EXTRA_ID, -1)!!
            productViewModel.update(product)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_search -> {
                val intent = Intent(baseContext,SearchActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_item_delete -> {
                productViewModel.deleteAllProducts()
                Toast.makeText(this,"Az összes elem törölve",Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
