package com.glimiafernandez.tippy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15


class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDescription: TextView
    private lateinit var switchBillSplit: Switch
    private lateinit var etSplitPersons: EditText
    private lateinit var numPersons: TextView
    private lateinit var amountSplit: TextView
    private lateinit var totalSplit: TextView
    private var amountStatus : Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipDescription = findViewById(R.id.tvTipDescription)
        switchBillSplit = findViewById(R.id.switchBillSplit)
        etSplitPersons = findViewById(R.id.etsplitPersons)
        amountSplit = findViewById(R.id.amountSplit)
        numPersons = findViewById(R.id.numPersons)
        totalSplit = findViewById(R.id.totalSplit)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        tvTipPercentLabel.text = "$INITIAL_TIP_PERCENT%"
        updateTipDescription(INITIAL_TIP_PERCENT)





        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvTipPercentLabel.text = "$progress%"
                computeTipAndTotal()
                updateTipDescription(progress)
                amountSplitBill(amountStatus)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipAndTotal()
                amountSplitBill(amountStatus)

            }

        })

        switchBillSplit.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                etSplitPersons.visibility = View.VISIBLE
                amountSplit.visibility = View.VISIBLE
                totalSplit.visibility = View.VISIBLE
                numPersons.visibility = View.VISIBLE
            } else {
                etSplitPersons.visibility = View.INVISIBLE
                amountSplit.visibility = View.INVISIBLE
                totalSplit.visibility = View.INVISIBLE
                numPersons.visibility = View.INVISIBLE
            }

        }

        etSplitPersons.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "after text change $p0")
                amountSplitBill(amountStatus)

            }

        })


    }


    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription = when (tipPercent) {
            in 0..9 -> "\uD83D\uDE21"
            in 10..14 -> "\uD83D\uDE36"
            in 15..19 -> "\uD83D\uDE42"
            in 20..24 -> "\uD83E\uDD2A"
            else -> "\uD83E\uDD29"
        }
        tvTipDescription.text = tipDescription


    }

    private fun computeTipAndTotal() {
        if (etBaseAmount.text.isEmpty()  ) {
            tvTipAmount.text = " "
            tvTotalAmount.text = " "
        return

        }
        val baseAmount = etBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress

        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)

        amountStatus = totalAmount


    }


    private fun amountSplitBill(double: Double) {
            if (etBaseAmount.text.isEmpty() or etSplitPersons.text.isEmpty()){
               amountSplit.text = " "
                return
            }
            val numPersons = etSplitPersons.text.toString().toDouble()
            val splitTotal = double / numPersons

            amountSplit.text = "%.2f".format(splitTotal)


        }

    }











