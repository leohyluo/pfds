package com.iebm.pfds.commons.exception;


/**
 * 异常等级
 */
public enum SpsoftExceptionLevel {
	/**
	 * 普通
	 * @return
   */
  NORMAL(1,"普通") ,
  /**
   * 严重级别
   */
  SEVERITY(2,"严重"),
  /**
   * 紧急
   */
  EMERGENCY(3,"紧急"),
  /**
   *  致命
   */
  DEADLY(4,"致命"),
  
    /**
   *  未知、无法识别
   */
  UNKNOW(5,"未知")
  ;
  private int value;
  private String nameVal;
  
  
  
  private SpsoftExceptionLevel(int value,String nameVal){
    this.value=value;
    this.nameVal = nameVal;
  }
  public int getValue(){
    return value;
  }
  
  public String getNameVal(){
    return nameVal;
  }
  
  /**
   * 是否需要发送短信
   * @return
   */
  public boolean isNeedSMS(){
    boolean result=false;
    switch(this){
      case NORMAL:result=false;break;
      case SEVERITY:
      case EMERGENCY:
      case DEADLY:result=true;break;
    }
    return result;
  }
  /**
   * 是否需要发送邮件
   * @return
   */
  public boolean isNeedMail(){
    boolean result=false;
    switch(this){
      case NORMAL:result=false;break;
      case SEVERITY:
      case EMERGENCY:
      case DEADLY:result=true;break;
    }
    return result;
  }
  /**
   * 是否需要发送页面提醒
   * @return
   */
  public boolean isNeedPrompt(){
    boolean result=false;
    switch(this){
      case NORMAL:
      case SEVERITY:
      case EMERGENCY:
      case DEADLY:result=true;break;
    }
    return result;
  }

  public static SpsoftExceptionLevel valueOf(int i) {
    switch (i) {
    case 1:
      return NORMAL;
    case 2:
      return SEVERITY;
    case 3:
      return EMERGENCY;
    case 4:
      return DEADLY;
    }
    return null;
  }
}
