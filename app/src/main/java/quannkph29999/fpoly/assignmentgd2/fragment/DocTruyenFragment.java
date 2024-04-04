package quannkph29999.fpoly.assignmentgd2.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import quannkph29999.fpoly.assignmentgd2.R;


public class DocTruyenFragment extends Fragment {
    String contentComic;
    PDFView pdfView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            contentComic = getArguments().getString("contentpdf", "");
            Log.d("pdf1", "onCreate: " + contentComic);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doc_truyen, container, false);
        pdfView = view.findViewById(R.id.pdfView);
        String pdfUrl = "http://192.168.0.101:3000/PDF/" + contentComic;
        new RetrievePdfStream().execute(pdfUrl);

        return view;
    }

    private class RetrievePdfStream extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                } else {
                    Log.e("PDFLoad", "Lỗi khi tải PDF, mã phản hồi: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            if (inputStream != null) {
                pdfView.fromStream(inputStream)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .pageFitPolicy(FitPolicy.WIDTH)
                        .load();

                // Kiểm tra xem PDF đã được tải thành công hay không
                if (pdfView.getPageCount() > 0) {
                    Log.d("PDFLoad", "PDF đã được tải và hiển thị thành công.");
                } else {
                    Log.d("PDFLoad", "Không thể hiển thị PDF: Định dạng không hợp lệ hoặc có lỗi khác.");
                }
            } else {
                Log.e("PDFLoad", "InputStream null, có lỗi khi tải PDF.");
            }
        }
    }
}

