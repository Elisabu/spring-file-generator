package com.bluex.manifiesto.pdf.models.Manifiesto;

import lombok.Data;

@Data
public class Manifiesto {
  private String document_id;
  private String retiro_id;
  private String client_identifier;
  private String create_date_document;
  private Courier courier;
  private File file;
  private Client client;
  private Carga carga;
  private String email;
  private Os[] os_list;
}
