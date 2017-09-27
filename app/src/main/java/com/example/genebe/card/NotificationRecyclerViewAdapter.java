package com.example.genebe.card;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.genebe.R;

import java.util.List;

/**
 * Created by 사하앍 on 2015-05-28.
 */
public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.CardViewHolder>{
    private List<NotificationCardInformation> notifications;

    public NotificationRecyclerViewAdapter(List<NotificationCardInformation> notifications){
        if (notifications == null){
            throw new IllegalArgumentException(
                    "Must Not be null"
            );
        }
        this.notifications = notifications;
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // Inflate the CardView for the genebe:
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_card_layout, viewGroup, false);
        // Create a ViewHolder to hold view references inside the CardView:
        CardViewHolder noti_cvh = new CardViewHolder(view);
        return noti_cvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        cardViewHolder.profilephoto.setImageResource(notifications.get(i).profilephotoID);
        cardViewHolder.title.setText(notifications.get(i).title);
        cardViewHolder.subtitle.setText(notifications.get(i).subtitle);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    /*Each PhotoViewHolder instance holds references to the ImageView and
     TextView of an associated row item, which is laid out in a CardView*/
    public class CardViewHolder extends RecyclerView.ViewHolder{
        ImageView profilephoto;
        TextView title;
        TextView subtitle;
        public CardViewHolder(View itemView) {
            super(itemView);
            profilephoto = (ImageView)itemView.findViewById(R.id.noti_profileimg);
            title = (TextView)itemView.findViewById(R.id.noti_title);
            subtitle = (TextView)itemView.findViewById(R.id.noti_subtitle);
        }

    }
}
