package com.octadata.pontolite.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tipo_escala", schema = "pontolite")
public class TipoEscala {

    public TipoEscala(long codigoTipoEscala, String nomeTipoEscala, long quantidadeHoraTrabalho) {
        this.codigoTipoEscala = codigoTipoEscala;
        this.nomeTipoEscala = nomeTipoEscala;
        this.quantidadeHoraTrabalho = quantidadeHoraTrabalho;
    }

    @Id
    @Column(name = "id_tipo_escala")
    private long codigoTipoEscala;

    @Column(name = "nm_tipo_escala")
    private String nomeTipoEscala;

    @Column(name = "qt_hora")
    private long quantidadeHoraTrabalho;

    public TipoEscala() {
    }

    public long getCodigoTipoEscala() {
        return codigoTipoEscala;
    }

    public void setCodigoTipoEscala(long codigoTipoEscala) {
        this.codigoTipoEscala = codigoTipoEscala;
    }

    public String getNomeTipoEscala() {
        return nomeTipoEscala;
    }

    public void setNomeTipoEscala(String nomeTipoEscala) {
        this.nomeTipoEscala = nomeTipoEscala;
    }

    public long getQuantidadeHoraTrabalho() {
        return quantidadeHoraTrabalho;
    }

    public void setQuantidadeHoraTrabalho(long quantidadeHoraTrabalho) {
        this.quantidadeHoraTrabalho = quantidadeHoraTrabalho;
    }

}
