/*
Melih Çelenk
info@melihcelenk.com
 */

package ysauygulama;


import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Arrays;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.error.MeanSquaredError;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.data.norm.MaxMinNormalizer;
import org.neuroph.util.data.norm.Normalizer;

public class YSA {
    
    MomentumBackpropagation bp;
    private final int epoch;
    private final int araKatmanNoron;
    private final int girisSayisi;
    private final int cikisSayisi;
    private DataSet[] subSets;
    private DataSet egitimVeriSeti;
    private DataSet testVeriSeti;
    private DataSet dataSet;
    private String agDosyaAdi; // "ogrenenAg.nnet"
    private String ogrenmisAgDosyaAdi;
    private String veriSetiDosyaYolu;
    
    private File sonKaydedilenAg(String dir) {
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {          
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod && file.getName().endsWith(".nnet") && !file.getName().equals("ogrenenAg.nnet")) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }
    public void sonKaydedilenAgiVer() throws Exception{
       String agAdi = this.sonKaydedilenAg("./").getName();
       ogrenmisAgVer(agAdi);
       System.out.println(agAdi + " isimli ağ verildi.");
    }
    /**
     * 
     * @param ogrenmisAgDosyaAdi Dosya Uzantısı .nnet olmalıdır.
     * @throws java.lang.Exception
     */
    public void ogrenmisAgVer(String ogrenmisAgDosyaAdi) throws Exception{
        if(ogrenmisAgDosyaAdi.endsWith(".nnet")) this.ogrenmisAgDosyaAdi = ogrenmisAgDosyaAdi;
        else throw new Exception();
    }
     /**
     * 
     * @param interlayerNeuronCount Ara Katman Nöron Sayısı
     * @param momentum Momentum
     * @param lambda Öğrenme Katsayısı
     * @param minError Minimum Hata Oranı
     * @param epoch Epoch Sayısı
     * @param inputsCount Girdi Sayısı
     * @param outputsCount Çıktı Sayısı
     */
    public YSA(int interlayerNeuronCount, double momentum, double lambda, double minError, int epoch, int inputsCount, int outputsCount){
        this("./src/ysaodev2/dosya.txt","ogrenenAg", interlayerNeuronCount, momentum, lambda, minError, epoch, inputsCount, outputsCount);
    }
         /**
     * 
     * @param agDosyaAdi Oluşturulacak .nnet uzantılı ağın dosya adı
     * @param interlayerNeuronCount Ara Katman Nöron Sayısı
     * @param momentum Momentum
     * @param lambda Öğrenme Katsayısı
     * @param minError Minimum Hata Oranı
     * @param epoch Epoch Sayısı
     * @param inputsCount Girdi Sayısı
     * @param outputsCount Çıktı Sayısı
     */
    public YSA(String agDosyaAdi,int interlayerNeuronCount, double momentum, double lambda, double minError, int epoch, int inputsCount, int outputsCount){
        this("./src/ysaodev2/dosya.txt",agDosyaAdi, interlayerNeuronCount, momentum, lambda, minError, epoch, inputsCount, outputsCount);
    }
    
    /**
     * 
     * @param veriSetiDosyaYolu Ağın Öğreneceği Veri Setinin Dosya Yolu - Örnek: ("./src/ysaodev2/dosya.txt") 
     * @param agDosyaAdi Ağın kaydedileceği dosyanın adı
     * @param interlayerNeuronCount Ara Katman Nöron Sayısı
     * @param momentum Momentum
     * @param lambda Öğrenme Katsayısı
     * @param minError Minimum Hata Oranı
     * @param epoch Epoch Sayısı
     * @param inputsCount Girdi Sayısı
     * @param outputsCount Çıktı Sayısı
     */
    public YSA(String veriSetiDosyaYolu,String agDosyaAdi,int interlayerNeuronCount, double momentum, double lambda, double minError, int epoch, int inputsCount, int outputsCount){
        
        this.epoch = epoch;
        this.araKatmanNoron = interlayerNeuronCount;
        this.girisSayisi = inputsCount;
        this.cikisSayisi = outputsCount;
        this.agDosyaAdi = agDosyaAdi + ".nnet";
        this.ogrenmisAgDosyaAdi = agDosyaAdi + ".nnet";
        this.veriSetiDosyaYolu = veriSetiDosyaYolu;
        
        bp = new MomentumBackpropagation();
        bp.setMomentum(momentum);
        bp.setLearningRate(lambda);
        bp.setMaxError(minError);
        bp.setMaxIterations(1);
        
        dataSet = DataSet.createFromFile(veriSetiDosyaYolu, inputsCount, outputsCount, ",", false);        
        
        Normalizer norm = new MaxMinNormalizer(dataSet);
        norm.normalize(dataSet);
        
        dataSet.shuffle();
        
        subSets = dataSet.split(0.70,0.30);
        egitimVeriSeti = subSets[0];
        testVeriSeti = subSets[1];
    }
    
    public void egit() throws FileNotFoundException{
        MultiLayerPerceptron sinirselAg = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,girisSayisi,araKatmanNoron,cikisSayisi);
        sinirselAg.setLearningRule(bp);
        double hata;

        for(int i=0; i<epoch; i++){
            sinirselAg.learn(egitimVeriSeti);
            if (i == 0 ) hata = 1;
            else hata = sinirselAg.getLearningRule().getPreviousEpochError();
        }
        sinirselAg.save(agDosyaAdi);
   }
    
    public void egitVeYazdir() throws FileNotFoundException{
        MultiLayerPerceptron sinirselAg = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,girisSayisi,araKatmanNoron,cikisSayisi);
        sinirselAg.setLearningRule(bp);
        double hata;

        for(int i=0; i<epoch; i++){
            sinirselAg.learn(egitimVeriSeti);
            if (i == 0 ) hata = 1;
            else hata = sinirselAg.getLearningRule().getPreviousEpochError();
            System.out.println(hata);
        }

        sinirselAg.save(agDosyaAdi);
        System.out.println("Eğitim Tamamlandı");
   }
    public double testEt(){
        NeuralNetwork sinirselAg = NeuralNetwork.createFromFile(ogrenmisAgDosyaAdi);        
        double toplamHata = 0;
        
        MeanSquaredError mse = new MeanSquaredError();
        for(DataSetRow r : testVeriSeti.getRows()){
            sinirselAg.setInput(r.getInput());
            sinirselAg.calculate();
            mse.addPatternError(sinirselAg.getOutput(),r.getDesiredOutput());
        }
        
        return mse.getTotalError();
    }
        
    public double testEtVeYazdir(){
        NeuralNetwork sinirselAg = NeuralNetwork.createFromFile(ogrenmisAgDosyaAdi);        
        double toplamHata = 0;
        
        MeanSquaredError mse = new MeanSquaredError();
        for(DataSetRow r : testVeriSeti.getRows()){
            sinirselAg.setInput(r.getInput());
            sinirselAg.calculate();
            System.out.println(Arrays.toString(r.getInput()) + " | " + Arrays.toString(r.getDesiredOutput()) + " | Çıktı:" + Arrays.toString(sinirselAg.getOutput()));
            mse.addPatternError(sinirselAg.getOutput(),r.getDesiredOutput());
        }
        
        return mse.getTotalError();
    }

    /**
     *
     * @param input Girdileri giriniz
     * @param desiredOutput Beklenen çıktıyı giriniz (tek çıktı için çalışır)
     * @param ciktiNo Almak istediğiniz çıktının indeksini giriniz
     * @return
     */
    public double TekSatirIcinCiktiHataGetir(double[]input,double[] desiredOutput,int ciktiNo){
        
        DataSet dstemp = DataSet.createFromFile(veriSetiDosyaYolu, girisSayisi, cikisSayisi, ",", false);
        DataSetRow r = new DataSetRow(input, desiredOutput);
        dstemp.add(r);
        Normalizer norm = new MaxMinNormalizer(dstemp);
        norm.normalize(dstemp);
        r = dstemp.getRows().get(dstemp.getRows().lastIndexOf(r));
        
        NeuralNetwork sinirselAg = NeuralNetwork.createFromFile(ogrenmisAgDosyaAdi);        
        
        sinirselAg.setInput(r.getInput());
        sinirselAg.calculate();
        System.out.println(Arrays.toString(r.getInput()) + " | " + r.getDesiredOutput()[0] + " | Çıktı:" + sinirselAg.getOutput()[0]);
        
        return r.getDesiredOutput()[ciktiNo] - sinirselAg.getOutput()[ciktiNo];
    }
    public double TekSatirIcinBeklenenCiktiyiAl(double[]input,int ciktiNo){
        DataSet dstemp = DataSet.createFromFile("./src/ysaodev2/dosya.txt", girisSayisi, cikisSayisi, ",", false);
        DataSetRow r = new DataSetRow(input,new double[cikisSayisi]);
        dstemp.add(r);
        Normalizer norm = new MaxMinNormalizer(dstemp);
        norm.normalize(dstemp);
        r = dstemp.getRows().get(dstemp.getRows().lastIndexOf(r));
        NeuralNetwork sinirselAg = NeuralNetwork.createFromFile(ogrenmisAgDosyaAdi);
        sinirselAg.setInput(r.getInput());
        sinirselAg.calculate();
        return sinirselAg.getOutput()[ciktiNo];
    }
 
    public void dataSetGoster(){
        for(DataSetRow dsr : dataSet){
            System.out.println(dsr.toString());
        }
    }
    
}
