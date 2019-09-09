package com.hfad.favourites;

import android.app.Application;

/**
 * Created by TUSHAR on 9/8/2019.
 */

public  class Global extends Application {
  private DatabaseHandler globdbcont;
  private String str;
  public  DatabaseHandler getdb()
  {
      return this.globdbcont;
  }
  public String getstr()
  {
      return this.str;
  }
  public void setstr(String str)
  {
      this.str=str;
  }
  public void setdata(DatabaseHandler db)
  {
      this.globdbcont = db;
  }
 }
