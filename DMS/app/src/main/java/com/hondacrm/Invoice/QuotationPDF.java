package com.hondacrm.Invoice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.hondacrm.BuildConfig;
import com.hondacrm.Module.QuotationResponse;
import com.hondacrm.R;
import com.hondacrm.Utils.RequestCodes;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLConnection;


@SuppressLint("UseCompatLoadingForDrawables")
public class QuotationPDF {

    Context context;
    File file;
    ProgressDialog progressDialog;

    Handler handle = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            progressDialog.incrementProgressBy(10);
        }
    };

    public void generatePDF(Context context, QuotationResponse quotationResponse) {

        this.context = context;

        createFolder();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMax(100);
        progressDialog.setMessage("Its downloading....");
        progressDialog.setTitle("Invoice downloading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDialog.getProgress() <= progressDialog.getMax()) {
                        Thread.sleep(200);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog.getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        generateInvoice(quotationResponse);

    }

    private void createFolder() {

        File myDirectory = new File(RequestCodes.directory_path);
        if (!myDirectory.exists()) {
            myDirectory.mkdirs();
        }

    }

    public void generateInvoice(QuotationResponse quotationResponse) {


        try {

            file = new File(RequestCodes.directory_path, quotationResponse.getQuotationN() + ".pdf");
            OutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.flush();

            PdfWriter pdfWriter = new PdfWriter(String.valueOf(file));
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.setDefaultPageSize(PageSize.A4);

            Document document = new Document(pdfDocument);

            DeviceRgb invoiceGreen = new DeviceRgb(51, 204, 51);
            DeviceRgb invoiceGray = new DeviceRgb(220, 220, 220);

            float[] columnWidth = {140, 140, 140, 140};
            Table table1 = new Table(columnWidth);

            Drawable drawable = context.getDrawable(R.drawable.logo);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bytes = stream.toByteArray();

            ImageData imageData = ImageDataFactory.create(bytes);
            Image image = new Image(imageData);
            image.setWidth(100f);

            //Table1 --- 01
            table1.addCell(new Cell(4, 1).add(image));
            /*  table1.addCell(new Cell().add(new Paragraph("")));*/
            /*  table1.addCell(new Cell().add(new Paragraph("")));*/
            /*  table1.addCell(new Cell().add(new Paragraph("")));*/

            //Table1 --- 02
            table1.addCell(new Cell().add(new Paragraph("Name: ")));
            table1.addCell(new Cell().add(new Paragraph(quotationResponse.getName())));
            table1.addCell(new Cell().add(new Paragraph("Quotation No: ")));
            table1.addCell(new Cell().add(new Paragraph(quotationResponse.getQuotationN())));

            //Table1 --- 03
            table1.addCell(new Cell().add(new Paragraph("Phone: ")));
            table1.addCell(new Cell().add(new Paragraph(quotationResponse.getPhone())));
            table1.addCell(new Cell().add(new Paragraph("Date: ")));
            table1.addCell(new Cell().add(new Paragraph(quotationResponse.getDate().substring(0, 10))));

            //Table1 --- 04
            table1.addCell(new Cell().add(new Paragraph("Address: ")));
            table1.addCell(new Cell().add(new Paragraph(quotationResponse.getAddress())));
           /* table1.addCell(new Cell().add(new Paragraph("")));
            table1.addCell(new Cell().add(new Paragraph("")));*/

            //Table1 --- 04
            table1.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

            float[] columnWidth2 = {60, 150, 150, 100, 100};
            Table table2 = new Table(columnWidth2);

            //Table2 --- 01
            table2.addCell(new Cell().add(new Paragraph("Sr No").setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.WHITE).setBackgroundColor(invoiceGreen)));
            table2.addCell(new Cell().add(new Paragraph("Description").setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.WHITE).setBackgroundColor(invoiceGreen)));
            table2.addCell(new Cell().add(new Paragraph("Amount").setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.WHITE).setBackgroundColor(invoiceGreen)));
            table2.addCell(new Cell().add(new Paragraph("Unit-Price").setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.WHITE).setBackgroundColor(invoiceGreen)));
            table2.addCell(new Cell().add(new Paragraph("Total Price").setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.WHITE).setBackgroundColor(invoiceGreen)));

            //Table2 --- 02
            table2.addCell(new Cell().add(new Paragraph("1").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("Unit Price")));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getUnitPrice())).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getUnitPrice())).setTextAlignment(TextAlignment.RIGHT));

            //Table2 --- 03
            table2.addCell(new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("CGST" + quotationResponse.getTaxId1Name())));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getTaxId1Name())).setTextAlignment(TextAlignment.RIGHT));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getTaxId1Name())).setTextAlignment(TextAlignment.RIGHT));

            //Table2 --- 04
            table2.addCell(new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("SGST" + quotationResponse.getTaxId2Name())));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getTaxId2Name())).setTextAlignment(TextAlignment.RIGHT));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getTaxId2Name())).setTextAlignment(TextAlignment.RIGHT));

            //Table2 --- 05
            table2.addCell(new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("KFC1%" + quotationResponse.getTaxId2Name())));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.RIGHT));
            table2.addCell(new Cell().add(new Paragraph("")).setTextAlignment(TextAlignment.RIGHT));

            //Table2 --- 07
            table2.addCell(new Cell().add(new Paragraph("2").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("ExShowRoom")));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getExShowroomPrice())).setTextAlignment(TextAlignment.RIGHT));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getExShowroomPrice())).setTextAlignment(TextAlignment.RIGHT));

            //Table2 --- 08
            table2.addCell(new Cell().add(new Paragraph("3").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("Insurance Amount")));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getInsuranceAmount())).setTextAlignment(TextAlignment.RIGHT));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getInsuranceAmount())).setTextAlignment(TextAlignment.RIGHT));

            double registrationRoadTax = quotationResponse.getRegistrationAmount() + quotationResponse.getRoadTax();

            //Table2 --- 09
            table2.addCell(new Cell().add(new Paragraph("4").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("Registration + Road Tax")));
            table2.addCell(new Cell().add(new Paragraph("₹ " + registrationRoadTax))).setTextAlignment(TextAlignment.RIGHT);
            table2.addCell(new Cell().add(new Paragraph("₹ " + registrationRoadTax))).setTextAlignment(TextAlignment.RIGHT);

            //Table2 --- 10
            table2.addCell(new Cell().add(new Paragraph("5").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("Accessories")));
            table2.addCell(new Cell().add(new Paragraph("₹ ")).setTextAlignment(TextAlignment.RIGHT));
            table2.addCell(new Cell().add(new Paragraph("₹ ")).setTextAlignment(TextAlignment.RIGHT));

            //Table2 --- 11
            table2.addCell(new Cell().add(new Paragraph("6").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("Road Side Assistance")));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getRsa())).setTextAlignment(TextAlignment.RIGHT));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getRsa())).setTextAlignment(TextAlignment.RIGHT));

            //Table2 --- 12
            table2.addCell(new Cell().add(new Paragraph("7").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("AMC")));
            table2.addCell(new Cell().add(new Paragraph("₹ ")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("₹ ")).setTextAlignment(TextAlignment.CENTER));

            //Table2 --- 13
            table2.addCell(new Cell().add(new Paragraph("8").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("Extended Warranty")));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getExtendedWarranty())).setTextAlignment(TextAlignment.RIGHT));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getExtendedWarranty())).setTextAlignment(TextAlignment.RIGHT));

            //Table2 --- 14
            table2.addCell(new Cell().add(new Paragraph("9").setTextAlignment(TextAlignment.CENTER)));
            table2.addCell(new Cell().add(new Paragraph("Others")));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getOtherTaxes())).setTextAlignment(TextAlignment.RIGHT));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getOtherTaxes())).setTextAlignment(TextAlignment.RIGHT));

            double totalAmount = quotationResponse.getExShowroomPrice() + quotationResponse.getInsuranceAmount() + registrationRoadTax
                    + quotationResponse.getRsa() + quotationResponse.getExtendedWarranty() + quotationResponse.getOtherTaxes();

            //Table2 --- 15
            table2.addCell(new Cell().add(new Paragraph("")));
            table2.addCell(new Cell().add(new Paragraph("")));
            table2.addCell(new Cell().add(new Paragraph("Sub Total")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("₹ " + quotationResponse.getSubtotal())).setTextAlignment(TextAlignment.RIGHT));

            //Table2 --- 16
            table2.addCell(new Cell().add(new Paragraph("")));
            table2.addCell(new Cell().add(new Paragraph("")));
            table2.addCell(new Cell().add(new Paragraph("Total")).setTextAlignment(TextAlignment.CENTER));
            table2.addCell(new Cell().add(new Paragraph("₹ " + totalAmount))).setTextAlignment(TextAlignment.RIGHT);

            float[] columnWidth3 = {560};
            Table table3 = new Table(columnWidth3);

            Drawable drawable1 = context.getDrawable(R.drawable.honda_footer);
            Bitmap bitmap1 = drawable1 != null ? ((BitmapDrawable) drawable1).getBitmap() : null;
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bytes1 = stream1.toByteArray();

            ImageData imageData1 = ImageDataFactory.create(bytes1);
            Image image1 = new Image(imageData1);
            image.setWidth(100f);

            //Table3 --- 01
            table3.addCell(new Cell().add(image1));

            document.add(table1);
            document.add(table2);
            document.add(table3);
            document.add(new Paragraph(""));
            document.add(new Paragraph("\n\n\n(Authorised Signatory)").setTextAlignment(TextAlignment.RIGHT));
            document.close();
            fileOutputStream.close();

            progressDialog.dismiss();

            Toast.makeText(context, "Invoice Downloaded", Toast.LENGTH_SHORT).show();
            openGeneratedPDF(file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void openGeneratedPDF(String pdfFilePath) {

        File file = new File(pdfFilePath);
        Intent intentShareFile = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        intentShareFile.setType(URLConnection.guessContentTypeFromName(file.getName()));
        intentShareFile.putExtra(Intent.EXTRA_STREAM, uri);
        intentShareFile.setDataAndType(uri, "application/pdf");
        intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intentShareFile, "Share Invoice"));

    }
}
