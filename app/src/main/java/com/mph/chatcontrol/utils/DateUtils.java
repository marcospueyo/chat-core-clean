package com.mph.chatcontrol.utils;
/* Created by macmini on 18/07/2017. */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    /** Número de segons en una hora */
    public final static long SECONDS_HOUR = 3600;

    /** Número de milisegons en una hora */
    public final static long MILLISECONDS_HOUR = SECONDS_HOUR * 1000;

    /** Número de milisegons en un dia */
    public final static long MILLISECONDS_DAY = MILLISECONDS_HOUR * 24;

    /** Número de milisegons en una setmana */
    public final static long MILLISECONDS_WEEK = MILLISECONDS_DAY * 7;

    /** Format de data complert ("dd/MM/yyyy HH:mm:ss") */
    public final static String FULL_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    /** Format de data ("dd/MM/yyyy") */
    public final static String DATE_FORMAT = "dd/MM/yyyy";

    /** Format de data ("dd/MM/yyyy") */
    public final static String DATE_TEXT_FORMAT = "dd LLLL yyyy";

    /** Format de hora complert ("HH:mm:ss") */
    public final static String TIME_FORMAT = "HH:mm:ss";

    /** Format de hora ("HH:mm") */
    public final static String SHORT_TIME_FORMAT = "HH:mm";

    /** Format de dia ("Fri") */
    public static final String LAST_MSG_DATE_FORMAT = "EE";


    /**
     * Si el format del paràmetre no existeix, obté el format per defecte
     *
     * @param format Format desitjat, pot ser null o buit per obtenir el format per defecte
     * @return El format obtingut
     */
    private static String getFormat(String format) {
        if (format == null || format.equals("")) return FULL_DATE_FORMAT;
        return format;
    }

    /**
     * Transforma el calendari a string utlitzant el format especificat com a paràmetre. Si no s'especifica format, s'agafa el format per defecte
     *
     * @param calendari El calendari a convertir en string
     * @param format El format amb el que es vol convertir el calendari
     * @return string amb el calendari transformat en funció del format escollit
     */
    public static String calendarToString(Calendar calendari, String format) {
        return dateToString(calendari.getTime(), format);
    }

    /**
     * Transforma la data a string utlitzant el format específicat com a paràmetre. Si no s'específica format, s'agafa el format per defecte
     *
     * @param date  La Date a convertir en string
     * @param format El format que es vol utlitzar en la conversió
     * @return string amb la Date transformada en funció del format escollit
     */
    public static String dateToString(Date date, String format) {
        return dateToString(date, format, Locale.getDefault());
    }

    /**
     * Transforma la data a string utlitzant el format específicat com a paràmetre. Si no s'específica format, s'agafa el format per defecte
     *
     * @param date  La Date a convertir en string
     * @param format El format que es vol utlitzar en la conversió
     * @param locale El Locale que s'utilitza per a les particularitats dels formats
     * @return string amb la Date transformada en funció del format escollit
     */
    public static String dateToString(Date date, String format, Locale locale) {
        format = getFormat(format);
        SimpleDateFormat df = new SimpleDateFormat(format, locale);
        try {
            String formattedDate = df.format(date.getTime());
            return formattedDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Transforma el string en Date utilitzant el format per defecte
     *
     * @param date String a transformar en Date
     * @return Date amb el valor del String
     */
    public static Date stringToDate(String date) {
        return stringToDate(date, null);
    }

    /**
     * Transforma el string en Date utilitzant el format especificat com a paràmetre. Si no s'especifica format, s'agafa el format per defecte
     *
     * @param date String a transformar en Date
     * @param format Format que es vol utlitzar en la conversió
     * @return Date amb el valor del String
     */
    public static Date stringToDate(String date, String format) {
        format = getFormat(format);
        SimpleDateFormat form = new SimpleDateFormat(format, Locale.getDefault());
        Date d;
        try {
            d = form.parse(date);
        } catch (java.text.ParseException e) {
            d = null;
        }
        return d;
    }

    /**
     * Transforma el string en Calendar utilitzant el format especificat com a paràmetre. Si no s'especifica format, s'agafa el format per defecte
     *
     * @param date the date
     * @param format Format que es vol utlitzar en la conversió
     * @return Calendar amb el valor del String
     */
    public static Calendar stringToCalendar(String date, String format) {
        Calendar c = Calendar.getInstance();
        c.setTime(stringToDate(date, format));
        return c;
    }

    /**
     * Retorna una instància de Calendar amb el valor de milisegons passats com a paràmetre
     *
     * @param milliseconds Valor de milisegons amb el que es vol configurar la instància de Calendar
     * @return Calendar amb el temps especificat
     */
    public static Calendar calendarSetTime(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);
        return c;
    }

    /**
     * Retorna una instància de Calendar amb el valor de la Date passada com a paràmetre
     *
     * @param date Date amb el valor que es vol configurar la instància de Calendar
     * @return Calendar amb el temps especificat
     */
    public static Calendar calendarSetTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * Comprova si el valor del par�metre pertany al dia d'avui
     *
     * @param date Date amb el valor a comprovar
     * @return True si pertany al dia d'avui, false altrament
     */
    public static boolean isToday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return isToday(c);
    }

    /**
     * Comprova si el valor del par�metre pertany al dia d'avui
     *
     * @param calendar Calendar amb el valor a comprovar
     * @return True si pertany al dia d'avui, false altrament
     */
    public static boolean isToday(Calendar calendar) {
        Calendar today = Calendar.getInstance();
        today.setTime(normalizeDate(today));
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime(today.getTime());
        tomorrow.add(Calendar.DATE, 1);
        return calendar.before(tomorrow) && calendar.after(today) || calendar.equals(today);
    }

    /**
     * Retorna una inst�ncia Calendar normalitzat amb el valor passat per par�metre, truncant a partir del valor del dia.
     *
     * @param calendar Calendar amb el valor a normalitzar
     * @return Calendar normalitzat
     */
    public static Calendar normalizeCalendar(Calendar calendar) {
        return normalizeCalendar(calendar.getTime());
    }

    /**
     * Retorna una inst�ncia Calendar normalitzat amb el valor passat per par�metre, truncant a partir del valor del dia.
     *
     * @param date Date amb el valor a normalitzar
     * @return Calendar normalitzat
     */
    public static Calendar normalizeCalendar(Date date) {
        Calendar normalized = Calendar.getInstance();
        normalized.setTime(stringToDate(dateToString(date, "dd/MM/yyyy"), "dd/MM/yyyy"));
        return normalized;
    }

    /**
     * Retorna una inst�ncia Calendar amb el valor actual normalitzat, truncant a partir del valor del dia.
     *
     * @return Calendar normalitzat
     */
    public static Calendar normalizeCalendar() {
        return normalizeCalendar(new Date());
    }

    /**
     * Retorna una Date normalitzada amb el valor passat per par�metre, truncant a partir del valor del dia.
     *
     * @param calendar Calendar amb el valor a normalitzar
     * @return Date normalitzada
     */
    public static Date normalizeDate(Calendar calendar) {
        return normalizeCalendar(calendar).getTime();
    }

    /**
     * Retorna una Date normalitzada amb el valor passat per par�metre, truncant a partir del valor del dia.
     *
     * @param date Date amb el valor a normalitzar
     * @return Date normalitzada
     */
    public static Date normalizeDate(Date date) {
        return normalizeCalendar(date).getTime();
    }

    /**
     * Retorna la Date actual normalitzada, truncant a partir del valor del dia.
     *
     * @return Date actual normalitzada
     */
    public static Date normalizeDate() {
        return normalizeDate(new Date());
    }

    public static String dateToStringISO8601(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",
                Locale.getDefault());
        return df.format(date);
    }

    public static Date stringToDateISO8601(String dateStr) {
        return stringToDate(dateStr, "yyyy-MM-dd'T'HH:mm:ssZ");
    }

    public static boolean dateIsInsideInterval(Date input, Date start, Date end) {
        return start.before(input) && end.after(input);
    }
}
