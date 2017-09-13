package com.example.listaprueba1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * helper for Prompt-Dialog creation
 */
public abstract class PromptDialog extends AlertDialog.Builder implements OnClickListener {
 private final EditText input1, input2;
 private final TextView texto1, texto2;
 //private final AlertDialog alert; 
 private final LinearLayout miLayout;
 
 /**
  * @param context
  * @param title resource id
  * @param message resource id
  */
 public PromptDialog(Context context) {
  super(context);
  
	setTitle("Creación de un nuevo botón");
	
	
	texto1 = new TextView(context);
	texto1.setText("Introduzca un título para el botón:");
	texto1.setTextColor(Color.WHITE);
	texto1.setTextSize(15);
	
	texto2 = new TextView(context);
	texto2.setText("Introduzca la URL de la página web:");
	texto2.setTextColor(Color.WHITE);
	texto2.setTextSize(15);
	
  	miLayout= new LinearLayout(context);
	miLayout.setOrientation(LinearLayout.VERTICAL);
	
	input1 = new EditText(context); 
	input2 = new EditText(context);
	
	miLayout.addView(texto1);
	miLayout.addView(input1);
	miLayout.addView(texto2);
	miLayout.addView(input2);
	
	setView(miLayout);
 
	setPositiveButton("Ok", this);
	setNegativeButton("Cancel", this);
 
 }
 
 /**
  * will be called when "cancel" pressed.
  * closes the dialog.
  * can be overridden.
  * @param dialog
  */
 public void onCancelClicked(DialogInterface dialog) {
  dialog.dismiss();
 }
 
 @Override
 public void onClick(DialogInterface dialog, int which) {
  if (which == DialogInterface.BUTTON_POSITIVE) {
   if (onOkClicked(  input1.getText().toString() , input2.getText().toString() )) {
    dialog.dismiss();
   }
  } else {
   onCancelClicked(dialog);
  }
 }
 
 /**
  * called when "ok" pressed.
  * @param input
  * @return true, if the dialog should be closed. false, if not.
  */
 abstract public boolean onOkClicked(String input1, String input2);
}