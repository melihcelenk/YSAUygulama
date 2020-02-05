# YSAUygulama

Omurga ile ilgili hasta olma durumunun belirlenmesi amacıyla veri setlerinden yola çıkılarak girilen değerlere
karşı sonuç tahmin edilmiş, gerçek değerlerle karşılaştırılmış ve hata yüzdesi ve veriler kullanıcıya gösterilmiştir. 

Java için yapay sinir ağları kütüphanesi olan Neuroph kullanılarak kullanıcının sadece girdi ve çıktı sayısını
belirleyerek ağı için en uygun modeli elde edebileceği bir model oluşturulmuştur. Bu kısım YSASecim.java sınıfı
tarafından yapılmaktadır. Yapay sinir ağının modeli ise YSA.java sınıfı içerisinde kurulmuştur.

YSA.java sınıfı kurucu metodunda ağın modelinin oluşturulması için ara katman nöron sayısı, momentum,
minimum hata, epoch sayısı, girdi sayısı, çıktı sayısı parametrelerini alır ve veri setinin rastgele %70’ini kullanarak
momentum back propagation ile ağı eğitir. Veri setinin kalan %30’u test için kullanılır.

YSASecim.java sınıfı kullanıcıdan sadece veri setini, girdi ve çıktı sayısını ister ve varsayılan olarak 23 farklı ağ
tasarımıyla 34 tekrar yaparak en düşük hata oranlı ağı kullanıcıya sunar. Kullanıcı bu ağı kaydedebilir ve YSA.java
sınıfına vererek veri seti için çıktı tahmininde bulunabilir. Ağ ve tekrar sayısı istenildiği takdirde kullanıcı tarafından
değiştirilebilir. Ana programda ise aşağıdaki ara yüz oluşturulmuştur.

Java’da momentum back propagation ile her bir hata tek tek kontrol edilemediğinden bp.setMaxIterations(1);
komutu ile 1 kez dönülmesi sağlanmış ve döngü manuel olarak içerde verilen epoch sayısına bakılarak yapılmıştır.
