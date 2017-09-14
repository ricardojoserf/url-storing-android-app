package fry.urlo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * helper for Prompt-Dialog creation
 */
public abstract class PromptDialog extends AlertDialog.Builder implements OnClickListener {
 private final EditText input1, input2;
 private final TextView texto1, texto2, texto3;
 //private final AlertDialog alert; 
 private final LinearLayout miLayout;
 
 /**
  */
 public PromptDialog(Context context) {
    super(context);

    setTitle("New url!");

    miLayout= new LinearLayout(context);
    miLayout.setOrientation(LinearLayout.VERTICAL);

    texto1 = new TextView(context);
    texto1.setText("\n\n");
    texto2 = new TextView(context);
    texto2.setText("\n\n");
    texto3 = new TextView(context);
    texto3.setText("\n\n");
    texto3.setTextSize(15);

    input1 = new EditText(context);
    input1.setHint("Title");
    input2 = new EditText(context);
    input2.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
    input2.setHint("URL");

    miLayout.addView(texto1);
    miLayout.addView(input1);
    miLayout.addView(texto2);
    miLayout.addView(input2);
    miLayout.addView(texto3);

    miLayout.setGravity(Gravity.CENTER);
    setView(miLayout);

    setNeutralButton("Cancel", this);
    setPositiveButton("OK", this);

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
  if (which == BUTTON_POSITIVE) {
   if (onOkClicked(  input1.getText().toString() , input2.getText().toString() )) {
    dialog.dismiss();
   }
  } else {
   onCancelClicked(dialog);
  }
 }
 
 /**
  * called when "ok" pressed.
  */
 abstract public boolean onOkClicked(String input1, String input2);
}