package com.fly2.utils;

import com.fly2.entities.Buchung;
import com.fly2.entities.Kunde;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.Cell.*;
import org.apache.poi.ss.usermodel.Row;

public class ExcelParser {

    private final Map<Integer, Kunde> kundenMap = new HashMap<>();
    private final RowParser rowParser = new RowParser();
    private FileInputStream fis;

    public ExcelParser(String path) throws FileNotFoundException, IOException {
        fis = new FileInputStream(new File(path));
        initRowsList();
    }

    public ExcelParser(File file) throws FileNotFoundException, IOException {
        fis = new FileInputStream(file);
        initRowsList();
    }

    private void initRowsList() throws IOException {
        //creating workbook instance that refers to .xls file  
        HSSFWorkbook wb = new HSSFWorkbook(fis);
        //creating a Sheet object to retrieve the object  
        HSSFSheet sheet = wb.getSheetAt(0);

        for (Row row : sheet) {
            if (isValidRow(row)) {
                Kunde kunde = rowParser.parseKunde(row);
                if (kundenMap.containsKey(kunde.getId())) {
                    updateBuchungen(kunde);
                } else {
                    kundenMap.put(kunde.getId(), kunde);
                }

            }
        }
    }

    /**
     * Vergleichen die Buchung der Kunde mit die Buchungen von der Kunde schon
     * gespeichert im kundenMap. Falls neue Buchung ist gleich oder neuer als
     * Buchung aus der kundenMap, wird die alte Buchung gelöscht und die neue
     * Buchung gespeichert
     */
    private void updateBuchungen(Kunde kunde) {
        Kunde mapKunde = kundenMap.get(kunde.getId());
        for (Buchung buchung : kunde.getBuchungs()) {
            Iterator<Buchung> iterator = mapKunde.getBuchungs().iterator();
            while (iterator.hasNext()) {
                Buchung _buchung = iterator.next();
                if (isGleicherFlug(buchung, _buchung)) {
                    Date buchungsDatum = buchung.getDatum();
                    Date mapBuchungsDatum = _buchung.getDatum();
                    if (!(mapBuchungsDatum.getTime() > buchungsDatum.getTime())) {

                        iterator.remove();
                    }
                }
            }
            mapKunde.addBuchung(buchung);
        }
    }

    /**
     * Return true falls die Buchung b1 ist für gleiches Flug wie die Buchung b2
     */
    private boolean isGleicherFlug(Buchung b1, Buchung b2) {
        int fluglinieId_1 = b1.getFlug().getFluglinie().getId();
        int fluglinieId_2 = b2.getFlug().getFluglinie().getId();
        if (fluglinieId_1 == fluglinieId_2) {
            Date datum_1 = b1.getFlug().getDatum();
            Date datum_2 = b2.getFlug().getDatum();
            if (datum_1.getTime() == datum_2.getTime()) {
                return true;
            }
        }
        return false;
    }

    public Set<Kunde> getKunden() {
        Set<Kunde> kunden = new HashSet<>();
        for (Kunde kunde : kundenMap.values()) {
            kunden.add(kunde);
        }
        return kunden;
    }

    private boolean isValidRow(Row row) {
        Cell fluggesellschaftKode = row.getCell(Column.FLUGGESELLSCHAFT_ID);
        Cell fluggesellschaftName = row.getCell(Column.FLUGGESELLSCHAFT_NAME);
        Cell fluglinieId = row.getCell(Column.FLUGLINIE_ID);
        Cell flughafenVonKode = row.getCell(Column.FLUGHAFEN_VON_CODE);
        Cell vonLand = row.getCell(Column.VON_LAND);
        Cell vonStadt = row.getCell(Column.VON_STADT);
        Cell flughafenNachKode = row.getCell(Column.FLUGHAFEN_NACH_CODE);
        Cell nachLand = row.getCell(Column.NACH_LAND);
        Cell nachStadt = row.getCell(Column.NACH_STADT);
        Cell flugDauer = row.getCell((Column.FLUG_DAUER));
        Cell flugDatum = row.getCell(Column.FLUG_DATUM);
        Cell flugPreis = row.getCell(Column.FLUG_PREIS);
        Cell flugzeugTyp = row.getCell(Column.FLUGZEUG_TYP);
        Cell hersteller = row.getCell(Column.HERSTELLER);
        Cell belegteSitze = row.getCell(Column.BELEGTE_SITZE);
        Cell sitzanzahl = row.getCell(Column.SITZANZAHL);
        Cell buchungsDatum = row.getCell(Column.BUCHUNGS_DATUM);
        Cell kundeId = row.getCell(Column.KUNDE_ID);
        Cell kundeName = row.getCell(Column.KUNDE_NAME);
        Cell kundeStadt = row.getCell(Column.ADRESSE_STADT);
        Cell kundeLand = row.getCell(Column.ADRESSE_LAND);

        if (fluggesellschaftKode == null || fluggesellschaftKode.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (fluggesellschaftName == null || fluggesellschaftName.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (fluglinieId == null || fluglinieId.getCellType() != CELL_TYPE_NUMERIC) {
            return false;
        } else if (flughafenVonKode == null || flughafenVonKode.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (vonLand == null || vonLand.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (vonStadt == null || vonStadt.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (flughafenNachKode == null || flughafenNachKode.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (nachLand == null || nachLand.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (nachStadt == null || nachStadt.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (flugDauer == null || flugDauer.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (flugDatum == null || flugDatum.getCellType() != CELL_TYPE_NUMERIC) {
            return false;
        } else if (flugPreis == null || flugPreis.getCellType() != CELL_TYPE_NUMERIC) {
            return false;
        } else if (flugzeugTyp == null || flugzeugTyp.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (hersteller == null || hersteller.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (belegteSitze == null || belegteSitze.getCellType() != CELL_TYPE_NUMERIC) {
            return false;
        } else if (sitzanzahl == null || sitzanzahl.getCellType() != CELL_TYPE_NUMERIC) {
            return false;
        } else if (buchungsDatum == null || buchungsDatum.getCellType() != CELL_TYPE_NUMERIC) {
            return false;
        } else if (kundeId == null || kundeId.getCellType() != CELL_TYPE_NUMERIC) {
            return false;
        } else if (kundeName == null || kundeName.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (kundeStadt == null || kundeStadt.getCellType() != CELL_TYPE_STRING) {
            return false;
        } else if (kundeLand == null || kundeLand.getCellType() != CELL_TYPE_STRING) {
            return false;
        }
        return true;
    }

}
