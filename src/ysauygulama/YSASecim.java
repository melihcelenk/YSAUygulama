/*
Melih Çelenk
info@melihcelenk.com
 */
package ysauygulama;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;


public class YSASecim {

    private final int gs;
    private final int cs;
    private int denemeMiktari;
    private int tekrar;
    private final YSA[] ysa;
    private final double hatalar[];
    private final double ayniAginHatasi[];
    public void setDenemeMiktari(int denemeMiktari){
        if(denemeMiktari > 0 && denemeMiktari<=23) this.denemeMiktari = denemeMiktari; else this.denemeMiktari = 23;        
    }
    public void setTekrar(int tekrar){
        if(tekrar > 0 ) this.tekrar = tekrar; else this.tekrar = 34;
    }
    
    public YSASecim(int girisSayisi, int cikisSayisi){
        this(girisSayisi,cikisSayisi,23,34);
    }
    public YSASecim(int girisSayisi, int cikisSayisi, int denemeMiktari, int tekrar){
        gs=girisSayisi;
        cs=cikisSayisi;
        
        if(denemeMiktari > 0 && denemeMiktari<=23)this.denemeMiktari = denemeMiktari; else this.denemeMiktari = 23;
        if(tekrar > 0 ) this.tekrar = tekrar; else this.tekrar = 34;
        
        ysa = new YSA[denemeMiktari];
        hatalar = new double[denemeMiktari];
        
        ayniAginHatasi = new double[denemeMiktari];
        
        aglariOlustur();
    }
    
    private void aglariOlustur(){
        if(0 < denemeMiktari)  ysa[0]  = new YSA("ysa"+0, 3,0.7,0.4,0.0001,3000,gs,cs); // 1 numaralı ağ
        if(1 < denemeMiktari)  ysa[1]  = new YSA("ysa"+1, 3,0.7,0.5,0.0001,3000,gs,cs);
        if(2 < denemeMiktari)  ysa[2]  = new YSA("ysa"+2, 3,0.7,0.6,0.0001,3000,gs,cs);
        if(3 < denemeMiktari)  ysa[3]  = new YSA("ysa"+3, 3,0.8,0.4,0.0001,3000,gs,cs);
        if(4 < denemeMiktari)  ysa[4]  = new YSA("ysa"+4, 3,0.8,0.5,0.0001,3000,gs,cs);
        if(5 < denemeMiktari)  ysa[5]  = new YSA("ysa"+5 ,3,0.8,0.6,0.0001,3000,gs,cs);
        if(6 < denemeMiktari)  ysa[6]  = new YSA("ysa"+6 ,3,0.8,0.7,0.0001,3000,gs,cs);
        if(7 < denemeMiktari)  ysa[7]  = new YSA("ysa"+7 ,3,0.9,0.4,0.0001,3000,gs,cs);
        if(8 < denemeMiktari)  ysa[8]  = new YSA("ysa"+8 ,3,0.9,0.5,0.0001,3000,gs,cs);
        if(9 < denemeMiktari)  ysa[9]  = new YSA("ysa"+9 ,3,0.9,0.6,0.0001,3000,gs,cs);
        if(10 < denemeMiktari) ysa[10] = new YSA("ysa"+10,2,0.7,0.5,0.0001,3000,gs,cs);
        if(11 < denemeMiktari) ysa[11] = new YSA("ysa"+11,2,0.8,0.6,0.0001,3000,gs,cs);
        if(12 < denemeMiktari) ysa[12] = new YSA("ysa"+12,2,0.7,0.5,0.0001,3000,gs,cs);
        if(13 < denemeMiktari) ysa[13] = new YSA("ysa"+13,2,0.8,0.6,0.0001,3000,gs,cs);
        if(14 < denemeMiktari) ysa[14] = new YSA("ysa"+14,2,0.9,0.6,0.0001,3000,gs,cs);
        if(15 < denemeMiktari) ysa[15] = new YSA("ysa"+15,2,0.9,0.5,0.0001,3000,gs,cs);
        if(16 < denemeMiktari) ysa[16] = new YSA("ysa"+16,4,0.9,0.6,0.0001,3000,gs,cs);
        if(17 < denemeMiktari) ysa[17] = new YSA("ysa"+17,4,0.9,0.5,0.0001,2000,gs,cs);
        if(18 < denemeMiktari) ysa[18] = new YSA("ysa"+18,3,0.9,0.5,0.0001,1000,gs,cs);
        if(19 < denemeMiktari) ysa[19] = new YSA("ysa"+19,3,0.9,0.5,0.0001,2000,gs,cs);
        if(20 < denemeMiktari) ysa[20] = new YSA("ysa"+20,3,0.9,0.5,0.0001,3000,gs,cs);
        if(21 < denemeMiktari) ysa[21] = new YSA("ysa"+21,3,0.9,0.5,0.0001,4000,gs,cs);
        if(22 < denemeMiktari) ysa[22] = new YSA("ysa"+22,3,0.9,0.5,0.0001,6000,gs,cs);
        if(23 < denemeMiktari) ysa[23] = new YSA("ysa"+23,3,0.9,0.5,0.0001, 500,gs,cs);
    }
    public void EgitveGoster() throws FileNotFoundException{
        PrintStream console = System.out;
        for(int i=0;i<denemeMiktari;i++){
            PrintStream fileStream = new PrintStream("grafikVeri"+i+".txt");
            System.setOut(fileStream);
            
            ysa[i].egitVeYazdir();
            hatalar[i] = ysa[i].testEt();
            
            System.out.println( (i+1) + " numaralı ağdaki hata: %" + new DecimalFormat("#0.000").format(hatalar[i]*100));
            ayniAginHatasi[i] += hatalar[i];
        }
        System.setOut(console); 
    }
    public void SecVeKaydet() throws FileNotFoundException {
        
        double minHata = Double.MAX_VALUE;
        int minHataTur = 0;
        int minHataAg = 0;
        for(int j=0;j<tekrar;j++){
            aglariOlustur();
                        
            System.out.println("-------" + (j+1) + ". Tur Başlangıcı--------------");
            for(int i=0;i<denemeMiktari;i++){
                ysa[i].egit();
                hatalar[i] = ysa[i].testEt();
                System.out.println( (i+1) + " numaralı ağdaki hata: %" + new DecimalFormat("#0.000").format(hatalar[i]*100));
                ayniAginHatasi[i] += hatalar[i];
            }

            int minHataIndex = getMinIndex(hatalar);

            System.out.println("Toplam Hatalar: " + Arrays.toString(ayniAginHatasi));
            System.out.println("");
            System.out.println("-> " + (j+1) + ". Turdaki En Düşük Hatalı Ağ: " + (minHataIndex + 1));
            System.out.println("");
            
            double enDusuk = Arrays.stream(hatalar).min().getAsDouble();
            if( enDusuk < minHata ) {
                minHata = Arrays.stream(hatalar).min().getAsDouble();
                minHataTur = j+1;
                minHataAg = minHataIndex;
                System.out.println("Şimdiye Kadarki En İyi Ağ " + minHataTur + ". Turdaki " + (minHataIndex + 1) + ". Ağ olarak sisteme kaydedildi.");
                System.out.println("");
                File f1 = new File("ysa"+ minHataIndex + ".nnet");
                File f2 = new File("enIyiAg.nnet");
                boolean b = f1.renameTo(f2);
            }
            
        }
        
        int minHataliAgIndex = getMinIndex(ayniAginHatasi);
        System.out.println(tekrar + " turluk test yapıldı.");
        System.out.println("Bütün Turlar Sonucunda Toplamda En Düşük Hata Yüzdesinin Olduğu Ağ Tasarımı: " + (minHataliAgIndex + 1));
        System.out.println("En Düşük Hata Yüzdesine Ulaşan Ağ ve Hata Yüzdesi: " + minHataTur + ". Turdaki " + (minHataAg+1) + " numaralı Ağ (%" + new DecimalFormat("#0.000").format(minHata*100) + ")");
        System.out.print("Ağı kaydetmek istiyor musunuz (E/H): ");
        Scanner in = new Scanner(System.in);
        char s = in.next().charAt(0);
        if(s == 'E' || s == 'e') {
            Date date = new Date() ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
            String tarih = dateFormat.format(date) + ".nnet";
            
            File f1 = new File("enIyiAg.nnet");
            File f2 = new File(tarih);
            boolean b = f1.renameTo(f2);
            
            System.out.println("Dosya " + tarih + " ismiyle kaydedildi.");
        }
        else System.out.println("Dosya kaydedilmedi.");
        
        aglariSil();
        
    }
    private int getMinIndex(double[] inputArray){ 
        double minValue = inputArray[0]; 
        int minValueIndex = 0;
        for(int i=0;i<inputArray.length;i++){ 
          if(inputArray[i] < minValue){ 
            minValue = inputArray[i];
            minValueIndex = i;
          } 
        } 
        return minValueIndex;
    }
    public void aglariSil(){
        for(int i=0;i<23; i++){
            try  
            { 
                File f= new File("ysa" + i + ".nnet");
                f.delete();
            }  
            catch(Exception e)  
            {  
            }
        }
    }
    
}
