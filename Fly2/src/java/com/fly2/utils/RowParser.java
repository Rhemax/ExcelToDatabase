package com.fly2.utils;

import com.fly2.entities.*;
import java.util.Date;
import org.apache.poi.ss.usermodel.*;

public class RowParser {

    public Kunde parseKunde(Row row) {
        Kunde kunde;
        Cell kategorieCell = row.getCell(Column.KUNDE_ANREDE);

        if (kategorieCell != null) {
            Kategorie kategorie = parseKategorie(kategorieCell);

            switch (kategorie.getName()) {
                case KategorieTyp.PERSON:
                    Person person = new Person();
                    Cell geschlechtCell = row.getCell(Column.KUNDE_ANREDE);
                    String geschlechtId = KategorieTyp.isMannlich(geschlechtCell.toString()) ? GeschlechtTyp.MANNLICH : GeschlechtTyp.WEIBLICH;
                    String geschlechtName = geschlechtId.equals(GeschlechtTyp.MANNLICH) ? "Mann" : "Frau";
                    person.setGeschlecht(new Geschlecht(geschlechtId, geschlechtName));
                    kunde = person;
                    break;
                case KategorieTyp.GESCHAFT:
                    kunde = new Geschaft();
                    break;
                default:
                    kunde = new Undefined();
                    break;
            }
        } else {
            kunde = new Undefined();
        }
        Cell idCell = row.getCell(Column.KUNDE_ID);
        int id = (int) Double.parseDouble(idCell.toString());
        kunde.setId(id);

        Land land = parseAdresseLand(row);
        kunde.setLand(land);

        Stadt stadt = parseAdresseStadt(row);
        kunde.setStadt(stadt);

        Cell nachnameCell = row.getCell(Column.KUNDE_NAME);
        String name = nachnameCell.toString();
        kunde.setName(name);

        Cell plzCell = row.getCell(Column.PLZ);
        if (!isCellEmpty(plzCell)) {
            String plz = plzCell.toString();
            if (plz.contains(".")) {
                plz = plz.substring(0, plz.indexOf('.'));
            }
            kunde.setPlz(plz);
        }

        Cell strasseCell = row.getCell(Column.KUNDE_STRASSE);
        if (!isCellEmpty(strasseCell)) {
            String strasse = strasseCell.toString();
            kunde.setStrasse(strasse);
        }

        Buchung buchung = parseBuchung(row);
        kunde.addBuchung(buchung);
        return kunde;
    }

    public Buchung parseBuchung(Row row) {
        Buchung buchung = new Buchung();
        Cell datumCell = row.getCell(Column.BUCHUNGS_DATUM);
        buchung.setDatum(datumCell.getDateCellValue());

        Flug flug = parseFlug(row);
        buchung.setFlug(flug);

        Cell anredeCell = row.getCell(Column.KUNDE_ANREDE);
        Kategorie kategorie = parseKategorie(anredeCell);
        buchung.setKategorie(kategorie);

        return buchung;
    }

    private Flug parseFlug(Row row) {
        Flug flug = new Flug();

        Cell datumCell = row.getCell(Column.FLUG_DATUM);
        Date datum = datumCell.getDateCellValue();
        flug.setDatum(datum);

        Cell dauerCell = row.getCell(Column.FLUG_DAUER);
        long dauer = parseSeconds(dauerCell);
        flug.setDauer(dauer);

        Fluglinie fluglinie = parseFluglinie(row);
        flug.setFluglinie(fluglinie);

        Flugzeug flugzeug = parseFlugzeug(row);
        flug.setFlugzeug(flugzeug);

        Cell preisCell = row.getCell(Column.FLUG_PREIS);
        double preis = Double.parseDouble(preisCell.toString());
        flug.setPreis(preis);

        return flug;
    }

    private long parseSeconds(Cell cell) {
        String raw = cell.toString();
        String[] arr = raw.split(":");
        int hours = Integer.parseInt(arr[0].trim());
        int minutes = Integer.parseInt(arr[1].trim());
        return minutes * 60 + hours * 60 * 60;
    }

    private Fluglinie parseFluglinie(Row row) {
        Fluglinie fluglinie = new Fluglinie();
        Cell idCell = row.getCell(Column.FLUGLINIE_ID);
        int id = (int) Double.parseDouble(idCell.toString());
        fluglinie.setId(id);

        Fluggeselschaft fluggeselschaft = parseFluggesellschaft(row);
        fluglinie.setFluggeselschaft(fluggeselschaft);

        Flughafen vonFlughafen = parseVonFlughafen(row);
        fluglinie.setVon(vonFlughafen);

        Flughafen nachFlughafen = parseNachFlughafen(row);
        fluglinie.setNach(nachFlughafen);

        return fluglinie;
    }

    private Flughafen parseVonFlughafen(Row row) {
        Flughafen flughafen = new Flughafen();
        Cell codeCell = row.getCell(Column.FLUGHAFEN_VON_CODE);
        flughafen.setCode(codeCell.toString());

        Stadt stadt = parseVonStadt(row);
        flughafen.setStadt(stadt);
        Land land = parseVonLand(row);
        flughafen.setLand(land);
        return flughafen;
    }

    private Flughafen parseNachFlughafen(Row row) {
        Flughafen flughafen = new Flughafen();
        Cell codeCell = row.getCell(Column.FLUGHAFEN_NACH_CODE);
        flughafen.setCode(codeCell.toString());

        Stadt stadt = parseNachStadt(row);
        flughafen.setStadt(stadt);
        Land land = parseNachLand(row);
        flughafen.setLand(land);
        return flughafen;
    }

    private Fluggeselschaft parseFluggesellschaft(Row row) {
        Fluggeselschaft fluggeselschaft = new Fluggeselschaft();
        Cell idCell = row.getCell(Column.FLUGGESELLSCHAFT_ID);
        fluggeselschaft.setId(idCell.toString());

        Cell nameCell = row.getCell(Column.FLUGGESELLSCHAFT_NAME);
        fluggeselschaft.setName(nameCell.toString());
        return fluggeselschaft;
    }

    private Land parseAdresseLand(Row row) {
        Cell landCell = row.getCell(Column.ADRESSE_LAND);
        return parseLand(landCell);
    }

    private Land parseVonLand(Row row) {
        Cell landCell = row.getCell(Column.VON_LAND);
        return parseLand(landCell);
    }

    private Land parseNachLand(Row row) {
        Cell landCell = row.getCell(Column.NACH_LAND);
        return parseLand(landCell);
    }

    private Stadt parseAdresseStadt(Row row) {
        Cell stadtCell = row.getCell(Column.ADRESSE_STADT);
        return parseStadt(stadtCell);
    }

    private Stadt parseVonStadt(Row row) {
        Cell stadtCell = row.getCell(Column.VON_STADT);
        return parseStadt(stadtCell);
    }

    private Stadt parseNachStadt(Row row) {
        Cell stadtCell = row.getCell(Column.NACH_STADT);
        return parseStadt(stadtCell);
    }

    private Flugzeug parseFlugzeug(Row row) {
        Flugzeug flugzeug = new Flugzeug();
        Hersteller hersteller = parseHersteller(row);
        flugzeug.setHersteller(hersteller);

        Cell typCell = row.getCell(Column.FLUGZEUG_TYP);
        flugzeug.setTyp(typCell.toString());

        Cell sitzAnzahlCell = row.getCell(Column.SITZANZAHL);
        int sitzanzahl = (int) Double.parseDouble(sitzAnzahlCell.toString());
        flugzeug.setSitzanzahl(sitzanzahl);
        return flugzeug;
    }

    private Hersteller parseHersteller(Row row) {
        Cell herstellerCell = row.getCell(Column.HERSTELLER);
        Hersteller hersteller = new Hersteller();
        hersteller.setName(herstellerCell.toString());
        return hersteller;
    }

    private Stadt parseStadt(Cell stadtCell) {
        Stadt stadt = new Stadt();
        stadt.setName(stadtCell.toString());
        return stadt;
    }

    private Land parseLand(Cell landCell) {
        Land land = new Land();
        land.setName(landCell.toString());
        return land;
    }

    private Kategorie parseKategorie(Cell kategorieCell) {
        Kategorie kategorie = new Kategorie();
        if (!isCellEmpty(kategorieCell)) {
            String anrede = kategorieCell.toString();
            if (KategorieTyp.isPerson(anrede)) {
                kategorie.setName(KategorieTyp.PERSON);
            } else if (KategorieTyp.isGeschaft(anrede)) {
                kategorie.setName(KategorieTyp.GESCHAFT);
            } else {
                kategorie.setName(KategorieTyp.UNDEFINED);
            }
        } else {
                kategorie.setName(KategorieTyp.UNDEFINED);
            }

        return kategorie;
    }

    public boolean isCellEmpty(final Cell cell) {
        if (cell == null) {
            return true;
        } else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return true;
        } else if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().trim().isEmpty()) {
            return true;
        }

        return false;
    }
    

}
