package shx.cotacaodolar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static String extrairData(String dataHora) throws ParseException {
        Date dataHoraCotacao = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dataHora);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(dataHoraCotacao);
    }

    public static String extrairHora(String dataHora) throws ParseException {
        Date dataHoraCotacao = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dataHora);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        return timeFormat.format(dataHoraCotacao);
    }
}
