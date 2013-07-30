package jp.morihirosoft.ninepatchsample;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.NinePatchDrawableFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Resources res = getResources();

		// Bitmap from runtime creation (Not compiled)
		Bitmap run_bmp = createSampleBitmap();

		((ImageView)findViewById(R.id.run_org)).setImageBitmap(run_bmp);
		findViewById(R.id.run_bd).setBackgroundDrawable(
				new BitmapDrawable(res, run_bmp));
		findViewById(R.id.run_nd).setBackgroundDrawable(
				NinePatchDrawableFactory.convertBitmap(res, run_bmp, null));

		// Bitmap from resource (Compiled)
		Bitmap res_bmp = BitmapFactory.decodeResource(res, R.drawable.sample);

		((ImageView)findViewById(R.id.res_org)).setImageBitmap(res_bmp);
		findViewById(R.id.res_bd).setBackgroundDrawable(
				new BitmapDrawable(res, res_bmp));
		findViewById(R.id.res_nd).setBackgroundDrawable(
				new NinePatchDrawable(res,
						new NinePatch(res_bmp, res_bmp.getNinePatchChunk(), null)));
		findViewById(R.id.res_nd_pad).setBackgroundDrawable(
				NinePatchDrawableFactory.convertBitmap(res, res_bmp, null));
		findViewById(R.id.res_nd_rid).setBackgroundResource(R.drawable.sample);
	}

	private Bitmap createSampleBitmap() {
		Bitmap b = Bitmap.createBitmap(65, 65, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		Paint p = new Paint();
		c.drawColor(Color.TRANSPARENT);
		p.setColor(Color.BLUE);
		c.drawCircle(32, 32, 30, p);
		p.setColor(Color.RED);
		c.drawLine( 2, 32, 63, 32, p);
		p.setColor(Color.GREEN);
		c.drawLine(32,  2, 32, 63, p);
		p.setColor(Color.BLUE);
		c.drawLine(32, 32, 33, 32, p);
		p.setColor(Color.BLACK);
		c.drawLine(32,  0, 33,  0, p); // Stretch L/R
		c.drawLine( 0, 32,  0, 33, p); // Stretch T/B
		c.drawLine(32, 64, 33, 64, p); // Padding L/R
		c.drawLine(64, 32, 64, 33, p); // Padding T/B
		p.setColor(Color.RED);
		c.drawLine( 1, 64, 16, 64, p); // OpticalBounds L
		c.drawLine(49, 64, 64, 64, p); // OpticalBounds R
		c.drawLine(64,  1, 64, 16, p); // OpticalBounds T
		c.drawLine(64, 49, 64, 64, p); // OpticalBounds B
		return b;
	}
}
