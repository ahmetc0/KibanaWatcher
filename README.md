# KibanaWatcher

Uygulama mikroservis verilerini microservices.json içerisinde saklıyor.

Hata sayısı üzerine çift tıklayınca kibana linki açılır. isteğe göre bu link değiştirilebilir.
Hata sayısı üzerine sağ tıklanıldığında sayaç sıfırlama, ayarlar, çıkış ve iklinci link seçeneği var. 
İsteğe göre çift tıklama ile son 15dk hataları, sağ tık ile gelen extra linke ise günlük hataların linki koyulabilir.

Ayarlar menüsünde tab bölgesine sağ tıklayarak yeni mikroservis ekleyebilir ya da çıkartabilriz. 
Kaydet yapmadıkça kaydetmez ve uygulamayı kapatıp açınca geri gelir eski hali.
Mikroservis adı kişisel olarak değiştirilebilir ancak diğer alanlar belirli standartta girilmelidir.
İsteğe göre query alanında değişiklik yapılabilir, bildiğimiz kibana search mantığındadır ancak url yazar gibi yani boşluk yerine %20 kullanmak gerekir.



Windows açılırken otomatik başlaması için .bat file hazırladım, eğer o çalışmazsa aşağıdaki yöntemi uygulayablirsiniz.

-Windows logo butonu ve R butonuna aynı anda basın, shell:startup yazın ve onaylayın. 
-Açılan klasöre KibanaWatcher.jar ın kısayolunu kopyalayın.


Görüş ve önerilere açığım :)
Ahmet Ceyhan