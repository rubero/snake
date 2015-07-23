package function;

import android.content.Context;
import android.view.View;

public class Screen extends View {
	public static int width;
	public static int height;
	
	public Screen(Context context) {
		super(context);		
	}
	
	// Определить рабочую ширину и высоту экрана
    @Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    width = MeasureSpec.getSize(widthMeasureSpec);
	    height = MeasureSpec.getSize(heightMeasureSpec);
	}
}
