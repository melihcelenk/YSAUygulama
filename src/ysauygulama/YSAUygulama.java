/*
Melih Çelenk
info@melihcelenk.com
 */
package ysauygulama;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class YSAUygulama {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.text.ParseException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws FileNotFoundException, ParseException, InterruptedException {
        YSA ysa = new YSA(3,0.8,0.5,0.0001,2000,6,1);
        // ysa.dataSetGoster();
        int sec = 0;
        Scanner in = new Scanner(System.in);
        do{ System.out.println("");
            System.out.println("1. Ağı Eğit");
            System.out.println("2. Ağı Test Et");
            System.out.println("3. Tek Veri İle Test Et");
            System.out.println("4. Farklı Parametrelerle Ağ Eğit ve En İyisini Gör");
            System.out.println("5. Herhangi Tek Veri İle Beklenen Çıktıyı Gör");
            System.out.println("6. Son Kaydedilen Ağı Yükle");
            System.out.println("7. Çıkış");
            System.out.println("");
            System.out.print("=>");
            sec = in.nextInt();
            switch(sec){
                case 1: // Ağı Eğit
                    ysa.egitVeYazdir();
                    break;
                case 2: // Ağıt Test Et
                    double testSonucu = (ysa.testEtVeYazdir()*100);
                    System.out.print("\nTest sonucu hata oranı: %");
                    System.out.println(new DecimalFormat("#0.00").format(testSonucu)); 
                    break;
                case 3: // Tek Satırı Test Et
                    double[] girisler = new double[6];
                    try {
                        System.out.print("1. Pelvic Incidence (26,15 ~ 129,83):");
                        girisler[0] = (Double.parseDouble(in.next().replace(",", "."))); //in.nextDouble();
                        System.out.print("2. Pelvic Tilt (-6,55 ~ 49,43):");
                        girisler[1] = (Double.parseDouble(in.next().replace(",", ".")));
                        System.out.print("3. Lumbar Lordosis Angle (14 ~ 125,74):");
                        girisler[2] = (Double.parseDouble(in.next().replace(",", ".")));
                        System.out.print("4. Sacral Slope (13,37 ~ 121,43):");
                        girisler[3] = (Double.parseDouble(in.next().replace(",", ".")));
                        System.out.print("5. Pelvic Radius (70,08 ~ 163,07):");
                        girisler[4] = (Double.parseDouble(in.next().replace(",", ".")));
                        System.out.print("6. Degree Spondylolisthesis (-11,06 ~ 418,54):");
                        girisler[5] = (Double.parseDouble(in.next().replace(",", ".")));
                        System.out.print("7. Çıktı (Tamsayı giriniz: 0 - Normal / 1 - Anormal): ");
                        double cikis = in.nextInt();
                        
                        double tekSatirTestSonucu = ysa.TekSatirIcinCiktiHataGetir(girisler,new double[]{cikis},0)*100;
                        
                        System.out.println("Girilen Değerler İçin Hata Oranı: %" + new DecimalFormat("#0.00").format(tekSatirTestSonucu));
                        
                    } catch (Exception e) {
                        System.out.println("Hatalı format girildi.");
                    }
                     
                     break;
                case 4:
                    YSASecim ysaSecim = new YSASecim(6,1,5,3);
                    ysaSecim.SecVeKaydet();
                break;
                case 5:
                    System.out.println(ysa.TekSatirIcinBeklenenCiktiyiAl(new double[]{48.92,19.96,40.26,28.95,119.32,8.03}, 0));
                    //ysa.dataSetGoster();
                break;
                case 6:
                    {
                        try {
                            ysa.sonKaydedilenAgiVer();
                        } catch (Exception ex) {
                            Logger.getLogger(YSAUygulama.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case 7:
                    break;
                case 8:
                    YSASecim ysaSecim2 = new YSASecim(6,1);
                    ysaSecim2.EgitveGoster();
                    ysaSecim2.aglariSil();
                    break;
                default:
                    System.out.println("Hatalı giriş yaptınız.");
                    Thread.sleep((long)1000);
                    
            }
        }while(sec != 7);
    }
    
}
