package shx.cotacaodolar.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Periodo {
    private Date dataInicial;
    private Date dataFinal;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy"); 

    public Periodo(String dataInicial, String dataFinal) throws ParseException{
        this.dataInicial = new SimpleDateFormat("MM-dd-yyyy").parse(dataInicial);
        this.dataFinal = new SimpleDateFormat("MM-dd-yyyy").parse(dataFinal);
    }

    public String getDataInicial(){
        return this.dateFormat.format(this.dataInicial);
    }

    public String getDataFinal(){
        return this.dateFormat.format(this.dataFinal);
    }

}
