package com.example.listaprueba1;

import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;


public class MainActivity extends Activity {

	 ListView lista;
	 ArrayAdapter<String> adaptador;
	 ArrayList<String> array = new ArrayList<String>();
	 ArrayList<String> arrayURLS = new ArrayList<String>();
	 Boolean borrar = false;
	
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        // Instancia de los dos botones:
	        final ImageButton botonURL = (ImageButton) findViewById(R.id.imageView2);
	        final ImageButton botonBorrar = (ImageButton) findViewById(R.id.imageView1);
	        
	        
	        
	        //Instancia de la lista:
	        lista = (ListView)findViewById(R.id.lista);

	        // Cargamos los valores de antes:
	        cargarBaseDeDatos();
	        
	        //Inicializar el adaptador con la fuente de datos
	        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);

	        //Relacionando la lista con el adaptador
	        lista.setAdapter(adaptador);
	        
	              
	        // 1 - Escucha del botón de añadir URL:
	        botonURL.setOnClickListener(new OnClickListener() {
		    	 public void onClick(View arg0) {		    		
		    		 crear();	
		    	 } 
			 });
	        
	        
	        // 2 - Escucha del botón de borrar:
	        botonBorrar.setOnClickListener(new OnClickListener() {
		    	 public void onClick(View arg0) {
		    		borrar();
		    	 }
			 });
	            
	          	 
	        // 3 - Escucha de los elementos de la lista:
	        lista.setOnItemClickListener(new OnItemClickListener() {
	        	@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					
	        		
	        		if (borrar == false) {
		        		int indice = (int) id;
		        		if(indice<arrayURLS.size()){
		        			goToUrl(arrayURLS.get(indice));
		        		}
		        		else{
		        			        			
		        		}
	        		}
	        		
	        		if (borrar == true) {
	        			
	        			// change the checkbox state
	                    CheckedTextView checkedTextView = ((CheckedTextView)view);
	                    checkedTextView.setChecked(!checkedTextView.isChecked());        			
	        		} 
					
				}

	        }); 
	        
	        

	    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.item1) {
			crear();
		}
		if (id == R.id.imageView1) {
			borrar(); 
		}
		if (id == R.id.imageView2) {
			ayuda();
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	/////////////////////////////////////////////////
		
	private void crear(){
	
		PromptDialog dlg = new PromptDialog(MainActivity.this) {
		
		@Override
		public boolean onOkClicked(String input1, String input2){
			array.add(input1);
			arrayURLS.add(input2);
			lista.setAdapter(adaptador);
			alta(lista,input1,input2);
			cambiaVisibilidad(true);
			return true; 
		}};
		
		dlg.show();
	
	}
	
	
	
	/////////////////////////////////////////////////
	
	private void borrar(){
		
  		 
		 if(!borrar){
			 			    		 		    		 
   		 ArrayAdapter<String>  adaptador2 = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_multiple_choice, array);
   		 
   		 lista.setAdapter(adaptador2);
   		 
   		 lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		 }
		 
		 if(borrar){
				   			
			 int count=lista.getCount();
       		
       		for(int i=(count - 1);i>=0;i--){
       		
       			if(lista.isItemChecked(i)){
       		    	 
       		    	String tituloABorrar = array.get(i).toString();
	                borrarBaseDeDatos(lista, tituloABorrar); 
       		    	 
       		    	array.remove(array.get(i));
                    arrayURLS.remove(arrayURLS.get(i)); 
       		    	 
       		    }
       			
       	 }
       		
   		 lista.setAdapter(adaptador);
   		 
		 }
		 
		 borrar = !(borrar);
		 
		 cambiaVisibilidad(false);
 
	}
	
	/////////////////////////////////////////////////
	
	private void ayuda(){
		
		
		
			Toast.makeText(this, 
					"URL Organiser v1.0 \n \n"
					+ "Autor: Ricardo Ruiz \n \n"
					+ "Funcionamiento: \n \n"
					+ "1- Copia un URL válido en el navegador. \n \n"
					+ "2- Abre la App y pulsa el botón de '+'. \n \n"
					+ "3- Añade un título y pega el URL. \n \n"
					+" Si quieres borrar uno o más enlaces, pulsa la papelera. \n"
					, Toast.LENGTH_SHORT).show();
			
	
	
	}
	
	/////////////////////////////////////////////////
	
	
	private void cambiaVisibilidad(boolean quitarTexto){
		
		TextView miTexto = (TextView) findViewById(R.id.textView2);
		
		if(quitarTexto){
			miTexto.setVisibility(View.INVISIBLE);
		}
				
		if((array.size()==0)&&(arrayURLS.size()==0)){			 
			 miTexto.setVisibility(View.VISIBLE);
		 }
		
		else{
			 miTexto.setVisibility(View.INVISIBLE);
		 }
		
	}
	
	
	/////////////////////////////////////////////////
	
	
	private void goToUrl (String url) {
		
		Uri uriUrl = Uri.parse("");
		
		if ( (url.contains("https:") )
		  || (url.contains("http:") )){
			uriUrl = Uri.parse(url);			
		}
		else{
			uriUrl = Uri.parse("http://" + url);
		}
		
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
				
	}
	
	/////////////////////////////////////////////////
	
	public void cargarBaseDeDatos(){
		
		// Cargamos los datos anteriores	        	        
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getReadableDatabase();
        
        if(bd!=null){
        	      	
        	String[] valores_recuperar = {"nombre", "url"};
            
        	Cursor c = bd.query("enlaces", valores_recuperar, null, null, null, null, null, null);
            
        	
            c.moveToFirst();
            
           
            while (!c.isAfterLast()) {
            
            	array.add		(c.getString(0));
            	arrayURLS.add	(c.getString(1));
            	c.moveToNext();
            	
            }
            
            c.close();
           
        }  
		
	}
	
	
	
	////////////////////////////////////////////////////////
	
	
	public void alta(View v, String input1, String input2) {
		
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
                
        String nombre 	= input1;
        String url 		= input2;
        
        ContentValues registro = new ContentValues();
        
        registro.put("nombre", nombre);
        registro.put("url", url);
       
        bd.insert("enlaces", null, registro);
        
        bd.close();
        
        Toast.makeText(this, "Enlace guardado correctamente.", Toast.LENGTH_SHORT).show();
    }


	
	////////////////////////////////////////////////////////
	
	
	public void borrarBaseDeDatos(View v, String titulo) {
		
		 AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
	     SQLiteDatabase bd = admin.getWritableDatabase();
	    
	     String[] whereArgs = new String[] { String.valueOf(titulo) };
	     bd.delete("enlaces","nombre=?",whereArgs);
	     
	     bd.close();
		
	}
	
	
	    


}
