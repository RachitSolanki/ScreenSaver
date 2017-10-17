
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ScreenSaver extends Thread {

    SignageActivity context;
    int x;
    int y;
    public static boolean isPlaying;

    float imageWidth=0;
    float imageHeight=0;

    ImageView imageView;
    RelativeLayout parentLayout;

    public static boolean loop;

    public ScreenSaver(int x,int y,ImageView imageView,SignageActivity context,RelativeLayout parentLayout) {
        this.x = x;
        this.y = y;
        this.imageView = imageView;

        this.context=context;
        this.parentLayout=parentLayout;

         imageWidth=imageView.getX();
         imageHeight=imageView.getY();
        loop = true;
        isPlaying=true;
    }


    public static void stopPlaying()
    {

        ScreenSaver.loop=false;
        ScreenSaver.isPlaying=false;
    }

    @Override
    public synchronized void start() {
        super.start();
    }



    @Override
    public void run() {
        super.run();

          int incx=8;
          int incy=6;

        while(loop)
        {


            if((imageWidth)<=0) //view is 0 go back
             incx=8;

            if((imageWidth+162)>this.x) //view is end go back
                incx=-8;

            if((imageHeight)<0) //view is 0 go back
                incy=6;

            if((imageHeight+162)>this.y)
                incy=-6;


            final int finalIncx = incx;
            final int finalIncy = incy;
            context.runOnUiThread(new Runnable() {
              @Override
              public void run() {

                  AnimatorSet animSetXY = new AnimatorSet();

                  ObjectAnimator y = ObjectAnimator.ofFloat(imageView,
                          "translationY",imageHeight,imageHeight+finalIncy);

                  ObjectAnimator x = ObjectAnimator.ofFloat(imageView,
                          "translationX", imageWidth, imageWidth+ finalIncx);

                  animSetXY.playTogether(x, y);
                  animSetXY.setInterpolator(new LinearInterpolator());
                  animSetXY.setDuration(80);
                  animSetXY.start();

                  imageHeight=imageHeight+finalIncy;
                  imageWidth = imageWidth+ finalIncx;

              }

            });

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();

            }

        }

    }

}
