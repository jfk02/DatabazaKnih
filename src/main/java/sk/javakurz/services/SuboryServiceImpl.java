package sk.javakurz.services;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import sk.javakurz.dao.DatabazaKnihDao;
import sk.javakurz.models.DatabazaKnih;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SuboryServiceImpl implements SuboryService {

    private final DatabazaKnihDao databazaKnihDao;
    private final String cestaKSuborom = "vystupykniznice/";
    private final String nazovSuboruZalohy = "kniznica.backup";

    /**
     * Vytvorí cestu k úložisku súborov databázy.
     *
     * @return True ak cesta je vytvorená inak false.
     */
    private boolean vytvorCestu() {
        boolean jeVytvorena = false;

        try {
            File priecinok = new File(cestaKSuborom);
            if (!priecinok.exists()) {
                priecinok.mkdir();
            }
            jeVytvorena = true;
        } catch (SecurityException e) {
            System.err.println("Priečinok pre ukladanie súborov knižnice sa nepodarilo vytvoriť!");
        }
        return jeVytvorena;
    }

    public boolean ulozDatabazu() {
        boolean jeUlozene = false;

        if (vytvorCestu()) {
            try {
                FileOutputStream suborNaUlozenie = new FileOutputStream(cestaKSuborom + nazovSuboruZalohy);
                ObjectOutputStream objektPreUlozenie = new ObjectOutputStream(suborNaUlozenie);
                objektPreUlozenie.writeObject(databazaKnihDao.getDatabazaKnih());
                objektPreUlozenie.close();
                suborNaUlozenie.close();
                jeUlozene = true;
                databazaKnihDao.setKniznicaZmenena(false);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return jeUlozene;
    }

    public boolean nacitajDatabazu() {
        boolean jeNahrata = false;

        try {
            FileInputStream suborPreOtvorenie = new FileInputStream(cestaKSuborom + nazovSuboruZalohy);
            ObjectInputStream vstupnyObjekt = new ObjectInputStream(suborPreOtvorenie);
            databazaKnihDao.setDatabazaKnih((DatabazaKnih) vstupnyObjekt.readObject());
            vstupnyObjekt.close();
            suborPreOtvorenie.close();
            jeNahrata = true;
            databazaKnihDao.setKniznicaZmenena(false);
        } catch (IOException | ClassNotFoundException e) {
            //System.err.println(e.getMessage());
            System.err.println("Nepodarilo sa načitať knižnicu z disku. Súbor nebol nájdený, alebo je poškodený!");
        }

        return jeNahrata;
    }

/*
    public boolean openPdfReport() {
        boolean isOpen = false;
        try {
            File file = new File(pdfPath);
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(file);
                isOpen = true;
            }
        } catch (DocumentException | IOException e) {
            System.err.println(e.getMessage());
        }
        return isOpen;
    }*/

    private Table vytvorTabulku() {

        Table tabulkaKnih = new Table(3);
        tabulkaKnih.setPadding(1f);
        tabulkaKnih.setSpacing(0.5f);
        tabulkaKnih.setWidth(100);
        var sirkyStlpcov = new float[]{50f, 40f, 10f};
        tabulkaKnih.setWidths(sirkyStlpcov);

        ArrayList<String> zahlavieTabulky = new ArrayList<>();
        zahlavieTabulky.add("Názov knihy");
        zahlavieTabulky.add("Autor");
        zahlavieTabulky.add("Rok vydania");

        zahlavieTabulky.forEach(e -> {
            Cell current = new Cell(new Phrase(e));
            current.setHeader(true);
            current.setHorizontalAlignment(HorizontalAlignment.CENTER);
            current.setVerticalAlignment(VerticalAlignment.CENTER);
            current.setBackgroundColor(Color.LIGHT_GRAY);
            tabulkaKnih.addCell(current);
        });

        LinkedHashMap<Integer, List<String>> listRiadkov = new LinkedHashMap<>();
        AtomicInteger indexRiadku = new AtomicInteger(1);

        databazaKnihDao.getVsetkyKnihy()
                .forEach(kniha -> {
                    listRiadkov.put(indexRiadku.get(),
                            Arrays.asList(kniha.getNazov(),
                                    kniha.getMenoAutora(),
                                    Integer.toString(kniha.getRokVydania())));
                    indexRiadku.incrementAndGet();
                });

        listRiadkov.forEach((index, knihaPodrobosti) -> {
            String aktualnyNazov = knihaPodrobosti.get(0);
            String aktualnyAutor = knihaPodrobosti.get(1);
            String aktualnyRokVydania = knihaPodrobosti.get(2);

            var bunkaNazov = new Cell(new Phrase(aktualnyNazov));
            var bunkaAutor = new Cell(new Phrase(aktualnyAutor));
            var bunkaRokVydania = new Cell(new Phrase(aktualnyRokVydania));

            bunkaNazov.setVerticalAlignment(VerticalAlignment.CENTER);
            bunkaAutor.setVerticalAlignment(VerticalAlignment.CENTER);
            bunkaRokVydania.setVerticalAlignment(VerticalAlignment.CENTER);
            bunkaRokVydania.setHorizontalAlignment(HorizontalAlignment.CENTER);

            tabulkaKnih.addCell(bunkaNazov);
            tabulkaKnih.addCell(bunkaAutor);
            tabulkaKnih.addCell(bunkaRokVydania);
        });

        return tabulkaKnih;
    }

    public boolean ulozDoPDF() {

        boolean jeUlozene = false;

        if (vytvorCestu()) {
            Document zoznamKnihPDF = new Document(PageSize.A4, 40, 40, 40, 40);

            String titulok = "Java Knižnica";
            String textDokumentu = "V našej knižnice sa nachádzajú nasledujúce knihy:";

            var zoznamKnih = vytvorTabulku();

            try {
                FileOutputStream pdfOutputFile = new FileOutputStream(cestaKSuborom + "ZoznamKníh.pdf");
                final PdfWriter pdfWriter = PdfWriter.getInstance(zoznamKnihPDF, pdfOutputFile);
                pdfWriter.setPageEvent(new PdfPageEventHelper() {
                    @Override
                    public void onEndPage(PdfWriter writer, Document document) {
                        writer.getDirectContent();
                    }
                });

                zoznamKnihPDF.open();  // Open the Document

                /* Here we add some metadata to the generated pdf */
                zoznamKnihPDF.addTitle("Java Knižnica");
                zoznamKnihPDF.addSubject("Zoznam kníh dostupných v Java Knižnici");
                zoznamKnihPDF.addKeywords("Java, Knižnica, OpenPDF");
                zoznamKnihPDF.addAuthor("Java Knižnica");
                /* End of the adding metadata section */

                // Create a Font object
                Font titleFont = new Font(Font.COURIER, 20f, Font.BOLDITALIC, Color.BLUE);

                // Create a paragraph with the new font
                Paragraph paragraph = new Paragraph(titulok, titleFont);

                // Element class provides properties to align
                // Content elements within the document
                paragraph.setAlignment(Element.ALIGN_CENTER);

                zoznamKnihPDF.add(paragraph);

                // Adding an empty line
                zoznamKnihPDF.add(new Paragraph(Chunk.NEWLINE));

                // Include the text as content of the pdf
                zoznamKnihPDF.add(new Paragraph(textDokumentu));

                // 4)Finally add the table to the document
                zoznamKnihPDF.add(zoznamKnih);

                zoznamKnihPDF.close();
                pdfWriter.close();
                jeUlozene = true;

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return jeUlozene;
    }

    public SuboryServiceImpl(DatabazaKnihDao databazaKnihDao) {
        this.databazaKnihDao = databazaKnihDao;
    }
}
