package de.markusfisch.android.barcodescannerviewdemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import de.markusfisch.android.barcodescannerview.widget.BarcodeScannerView;
import de.markusfisch.android.barcodescannerviewdemo.R;
import de.markusfisch.android.barcodescannerviewdemo.activity.model.ScannerRequest;
import de.markusfisch.android.barcodescannerviewdemo.activity.model.ScannerResponse;
import de.markusfisch.android.barcodescannerviewdemo.activity.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
	private static final int REQUEST_CAMERA = 1;
	public  String Barcode_String, Ticket_Status;
	private BarcodeScannerView scannerView;
	Dialog dialog;
	@Override
	public void onRequestPermissionsResult(
			int requestCode,
			String[] permissions,
			int[] grantResults) {
		if (requestCode == REQUEST_CAMERA &&
				grantResults.length > 0 &&
				grantResults[0] != PackageManager.PERMISSION_GRANTED) {
			Toast.makeText(this, R.string.error_camera,
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		checkPermissions();
		setContentView(R.layout.activity_main);
		TextView textView = findViewById(R.id.text);

		scannerView = findViewById(R.id.scanner);
		scannerView.setCropRatio(.75f);
		scannerView.setOnBarcodeListener(result -> {
			// This listener is called from the Camera thread.
			textView.post(() -> textView.setText(result.getText()));
			Barcode_String = result.getText();
			dialog = new Dialog(MainActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.dialog_wait);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			getScan(getScanValue());
			// Return true to keep scanning for barcodes.
			return false;
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		scannerView.openAsync(BarcodeScannerView.findCameraId(
				Camera.CameraInfo.CAMERA_FACING_BACK));
	}

	@Override
	public void onPause() {
		super.onPause();
		scannerView.close();
	}

	private void checkPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			String permission = android.Manifest.permission.CAMERA;
			if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{permission}, REQUEST_CAMERA);
			}
		}
	}

	private void getScan(ScannerRequest scanRequest) {
		Call<ScannerRequest> scanRequestCall = ApiService.getTicketApiService().getScan(scanRequest);
		scanRequestCall.enqueue(new Callback<ScannerRequest>() {
			@Override
			public void onResponse(Call<ScannerRequest> call, Response<ScannerRequest> response) {
				if(response.isSuccessful())
				{
					ScannerRequest scannerRequest = response.body();
					String status = scannerRequest.getStatus_code();

					Log.i("Status Code.........", status);
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("Ticket Confirmation");
					if(status.toString().equals("VALID")){
						builder.setMessage("Valid Ticket, Welcome!");
						builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								finish();
							}
						});
						builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								finish();
							}
						});
						AlertDialog dialog = builder.create();
						dialog.show();
					}else{
						builder.setMessage("Error: Invalid Ticket, Dismiss!");
						builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								finish();
							}
						});
						builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
								finish();
							}
						});
						AlertDialog dialog = builder.create();
						dialog.show();
					}

				}

			}

			@Override
			public void onFailure(Call<ScannerRequest> call, Throwable t) {

			}
		});
	}

	// Scan Request

	public ScannerRequest getScanValue() {
		ScannerRequest scanRequest = new ScannerRequest();
		scanRequest.setBarcode(Barcode_String);
		return scanRequest;
	}


}
