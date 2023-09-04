package com.bluex.manifiesto.pdf.models.Manifiesto;

import java.sql.Date;

import lombok.Data;

@Data
public class Email {
  private String subject;
  // private String for;
  private String template;
  private String status;
  private String default_email;
  private Date updated_at;
}
