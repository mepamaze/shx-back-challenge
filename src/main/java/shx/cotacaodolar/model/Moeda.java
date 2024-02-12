package shx.cotacaodolar.model;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;



public class Moeda implements Serializable{

    @NotNull(message = "O preço não pode ser nulo")
    @Min(value = 0, message = "O preço não pode ser negativo")
    public Double preco_compra;

    @NotNull(message = "O preço não pode ser nulo")
    @Min(value = 0, message = "O preço não pode ser negativo")
    public Double preco_venda;

    @NotNull(message = "A data não pode ser nula")
    public String data;

    @NotNull(message = "A hora não pode ser nula")
    public String hora;

    public String toString(){
        return preco_compra.toString() + '_' + preco_venda.toString() + '_' + data.toString() + '_' + hora;
    }
}
