package com.devcambo.api.constant;

public class AppConstants {

  /*
   * JWT Constants
   * */
  public static final String DEFAULT_JWT_SECRET = "04d11301d5782cb7dc249e8168f22a7d";
  public static final String JWT_SECRET = "JWT_SECRET";
  public static final String JWT_HEADER = "Authorization";
  public static final int JWT_EXPIRATION = 24 * 60 * 60 * 1000;

  /*
   * S3 Constants
   * */
  public static final String S3_BUCKET_NAME = "devcambo-bucket";

  private AppConstants() {}
}
