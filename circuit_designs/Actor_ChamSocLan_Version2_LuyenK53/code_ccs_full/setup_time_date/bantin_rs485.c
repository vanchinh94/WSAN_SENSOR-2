// ham cho chuc nang dung trong mode 2:
// mode 2 : mode su dung rs485 de do gia tri cua nhiet do
// va trong chuong tirnh nay co thiet lap 1 che do nhiet do va do am nhat dinh
// cac van duoc dieu khien theo nhiet do
// neu nhiet do hoac do am xuong duoi nguong thi bat het tat ca ca van
// them ham dieu khien van
#include "Dieukhienvan.c"
//khai bao cac pin io
#define RS_485_EN    PORTC.1
#define RS_485_EN_IO DDRC.1
// khoi tao cac bien luu gia tri min cua nhiet do va do am
// cac gia tri nay duoc luu tru trong eeprom cua 24LC01
unsigned char temp_max;    // gia tri nhiet do max
unsigned char huff_max;    // gia tri do am max
unsigned char temp,huff;
// khai bao cac bien chon che do

// ham setup io cho chan dieu khien en rs485
void setup_rs485_io
{
   // khoi tao pin dieu khien en rs485 la output
   RS_485_EN_IO=1;
   temp_max=eeprom_read();// doc gia tri nhiet do max 
   huff_max=eeprom_read();// doc gia tri do am max 
}

// ham su ly du lieu nhan duoc tu uart
// dinh ki 1 phut gui ban tin nay qua uart 
void mode3_uart_send()
{
   // bat che do send uart len roi gui du lieu nhan duoc ra uart
   RS_485_EN=1;
   // send ban tin cap nhat nhiet do hoac do am qua rs485
   lcd_putchar(0x01);
   // sau khi gui lenh xong dua vao che do nhan du lieu
   RS_485_EN=0;  
}
//ham su ly du lieu nhan tu uart 
// ban tin co dinh dang "TEMP,HUFF"
// ban tin gom 2 byte
// ham nay duoc goi trong ngat uart
void mode3_uart_rev()
{
   unsigned char buff_rx[3];
   unsigned char rx_count;
   //lay gia tri nhiet do 
   temp=buff_rx[0];
   huff=buff_rx[1];
   // check xem neu nhu nhu gia tri nhiet do hoac do am nho hon nguong thi bat van. neu khong thi tat van
   if(temp>temp_max || huff< huff_max)
      on_all_van();
   else
      off_all_van();
}

// ham chinh trong mode 3
// 
void mode3_main()
{
    //neu bam nut menu
    // lua chon cac che do khac nhau trong setup hoac hien thi
    if(!MENU_BTN)         
    {
        // cho den khi nut duoc nha ra
        while(!MENU_BTN);
        // bat loa len phat hien ra la nut duoc an
        Speaker = 1;
        delay_ms(150);
        Speaker = 0;
        delay_ms(150);
        // tang gia tri bien len de cho cac che do
        mode3_state++;
        if(mode3_state==3)        set_flagtiet=1;  
    }
    switch(mode3_state)
    {
      // che do hien thi gia tri cua nhiet do va do am
      case 1:
      {  
         // hien thi gia tri max va min 
         lcd_gotoxy(1,1);
         // hien thi gia tri cua nhiet do va do am cua moi truong hien tai
         lcd_gotoxy(2,1);
      }
      break;
      // chuong trinh setup cac gia tri nguong cua nhiet do va do am
      case 2:
      {
         lcd_gotoxy(1,1);
         // hien thi che do setup
         lcd_puts("SETUP gia tri nguong");
         // hien thi gia tri nguong
         lcd_gotoxy(4,0); 
         lcd_blink_on(); 
         delay_ms(10);  
         lcd_gotoxy(0,1);
         if(enable[STT_tiet]==1)  
             lcd_puts("EN ");
          else
                lcd_puts("DIS");
         if(!SET_BTN)         
         {
            // cho den khi nut duoc nha ra
            while(!MENU_BTN);
            // bat loa len phat hien ra la nut duoc an
        Speaker = 1;
        delay_ms(150);
        Speaker = 0;
        delay_ms(150);
        // tang gia tri bien len de cho cac che do
        mode3_state++;
        if(mode3_state==3)        set_flagtiet=1;  
    }
            if(!UP_BTN)            //neu bam button add------
            {
            //cho toi khi tha nut bam
                Speaker = 1;
                delay_ms(150);
                Speaker = 0;
                delay_ms(150);
                STT_tiet++;
                if(STT_tiet==13)        STT_tiet=1; 
                put_time(STT_tiet,4,0);  
                put_time(h_in[STT_tiet],11,0);
                put_time(m_in[STT_tiet],14,0);
                put_time(h_out[STT_tiet],11,1);
                put_time(m_out[STT_tiet],14,1); 
                lcd_gotoxy(0,1);
                if(enable[STT_tiet]==1)  
                    lcd_puts("EN ");
                else
                    lcd_puts("DIS");
            }

        if(!DOWN_BTN)          //neu bam nut sub--------
        {
            //cho toi khi tha nut bam
            Speaker = 1;
            delay_ms(150);
            Speaker = 0;
            delay_ms(150); 
            STT_tiet--;
            if(STT_tiet==0)        STT_tiet=12;   
            //lcd_gotoxy(5,0);
            put_time(STT_tiet,4,0); 
            put_time(h_in[STT_tiet],11,0);
            put_time(h_in[STT_tiet],11,0);
            put_time(m_in[STT_tiet],14,0);
            put_time(h_out[STT_tiet],11,1);
            put_time(m_out[STT_tiet],14,1); 
            lcd_gotoxy(0,1);
                if(enable[STT_tiet]==1)  
                    lcd_puts("EN ");
                else
                    lcd_puts("DIS");
        }  
      }
      break;
    }
     
}

// ham setup cac gia tri max va min cho temp va huff
void mode3_dislay