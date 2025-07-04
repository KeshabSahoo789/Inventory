package com.pharmacy.backend.controller;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pharmacy.backend.model.CartItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    public static ByteArrayOutputStream generateBillPDF(String name, String phone, List<CartItem> cart) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitle = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font fontBody = new Font(Font.HELVETICA, 12);

            document.add(new Paragraph("Pharmacy Bill", fontTitle));
            document.add(new Paragraph("Customer Name: " + name, fontBody));
            document.add(new Paragraph("Mobile: " + phone, fontBody));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.addCell("Product");
            table.addCell("Quantity");
            table.addCell("Price");
            table.addCell("Total");

            double subtotal = 0;
            for (CartItem item : cart) {
                double total = item.getPrice() * item.getQuantity();
                subtotal += total;

                table.addCell(item.getProductName());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell("₹" + item.getPrice());
                table.addCell("₹" + total);
            }

            document.add(table);
            document.add(new Paragraph(" "));

            double gst = subtotal * 0.18;
            double total = subtotal + gst;

            document.add(new Paragraph("Subtotal: ₹" + String.format("%.2f", subtotal)));
            document.add(new Paragraph("GST (18%): ₹" + String.format("%.2f", gst)));
            document.add(new Paragraph("Total: ₹" + String.format("%.2f", total)));

        } catch (DocumentException e) {
            throw new IOException("Error while generating PDF", e);
        } finally {
            document.close();
        }

        return out;
    }
}
