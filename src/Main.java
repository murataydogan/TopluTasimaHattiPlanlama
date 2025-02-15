import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // mahalleler ve kriterler için rastgele sentetik veriler oluşturma
        String[] mahalleler = {"Mahalle A", "Mahalle B", "Mahalle C"};


        double[][] kriterler = new double[3][5]; // 3 mahalle, 5 kriter
        Random rastgele = new Random();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                kriterler[i][j] = 1 + rastgele.nextDouble() * 9; // 1 ile 10 arasında değerler
            }
        }

        // softmax algoritması ile ağırlıkları hesaplama
        double[] skorlar = softmax(kriterler);

        // maliyet-Fayda analizi
        double[] maliyetFaydaOranlari = maliyetFaydaAnalizi(kriterler);

        // en iyi güzergahı belirleme
        int eniyiGuzergah = eniyiGuzergahiBul(maliyetFaydaOranlari);

        // sonuçları yazdırma
        sonuclariYazdir(mahalleler, kriterler, skorlar, maliyetFaydaOranlari, eniyiGuzergah);
    }

    public static double[] softmax(double[][] kriterler) {
        double[] skorlar = new double[kriterler.length];
        double toplamExp = 0.0;

        for (int i = 0; i < kriterler.length; i++) {
            double agirlikliToplam = 0.0;
            for (int j = 0; j < kriterler[i].length; j++) {
                agirlikliToplam += kriterler[i][j];
            }
            skorlar[i] = Math.exp(agirlikliToplam);
            toplamExp += skorlar[i];
        }

        for (int i = 0; i < skorlar.length; i++) {
            skorlar[i] /= toplamExp;
        }

        return skorlar;
    }

    public static double[] maliyetFaydaAnalizi(double[][] kriterler) {
        double[] oranlar = new double[kriterler.length];

        for (int i = 0; i < kriterler.length; i++) {
            double fayda = kriterler[i][0] + kriterler[i][1] + kriterler[i][3] + kriterler[i][4]; // nüfus yoğunluğu,ulaşım altyapısı,çevresel etki,sosyal fayda
            double maliyet = kriterler[i][2]; // maliyet
            oranlar[i] = fayda / maliyet; // Fayda-Maliyet oranı
        }

        return oranlar;
    }

    public static int eniyiGuzergahiBul(double[] oranlar) {
        int eniyi_indeks = 0;
        double maxOran = oranlar[0];

        for (int i = 1; i < oranlar.length; i++) {
            if (oranlar[i] > maxOran) {
                maxOran = oranlar[i];
                eniyi_indeks = i;
            }
        }

        return eniyi_indeks;
    }

    public static void sonuclariYazdir(String[] mahalleler, double[][] kriterler, double[] skorlar, double[] maliyetFaydaOranlari, int eniyiGuzergah) {
        System.out.println("Mahalle | Nüfus Yoğunluğu | Ulaşım Altyapısı | Maliyet | Çevresel Etki | Sosyal Fayda | Softmax Skoru | Maliyet-Fayda Oranı");
        for (int i = 0; i < mahalleler.length; i++) {
            System.out.printf("%-10s | %-15.2f | %-15.2f | %-7.2f | %-12.2f | %-12.2f | %-7.4f | %-7.4f\n",
                    mahalleler[i], kriterler[i][0], kriterler[i][1], kriterler[i][2], kriterler[i][3], kriterler[i][4], skorlar[i], maliyetFaydaOranlari[i]);
        }

        System.out.println("\nMaliyet-Fayda analizi ile en uygun toplu taşıma hattı:" + mahalleler[eniyiGuzergah]);
    }
}
