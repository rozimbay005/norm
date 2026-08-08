# SMS Forwarder — SMS'larni Telegram botga forward qiluvchi ilova

## Qanday ishlaydi
Telefonga SMS kelganda, ilova uni ushlab, siz kiritgan Telegram bot orqali
sizga (yoki belgilagan chatga) yuboradi.

## APK yasash (noutbukda Android Studio kerak emas)

1. **GitHub'da yangi repository oching** (masalan: `sms-forwarder`).
2. Ushbu papkadagi barcha fayllarni o'sha repositoryga yuklang (upload qiling
   yoki `git push` qiling).
3. GitHub'da repository ichida **Actions** bo'limiga o'ting.
4. "Build APK" workflow'ni tanlab, **Run workflow** tugmasini bosing
   (yoki `main` branchga push qilsangiz avtomatik ishga tushadi).
5. Bir necha daqiqadan so'ng, tugagan workflow ichidan **Artifacts** bo'limida
   `sms-forwarder-apk` faylini yuklab olasiz — bu tayyor APK.

Bularning barchasi GitHub serverlarida (bulutda) bajariladi, noutbukingiz
faqat kod yozish va faylni yuklash uchun ishlatiladi.

## Telegram Bot Token va Chat ID qanday olinadi

1. Telegram'da **@BotFather** ga yozib, `/newbot` buyrug'i bilan yangi bot
   yarating — sizga **Bot Token** beriladi.
2. O'sha botga o'zingiz `/start` deb yozing.
3. **Chat ID**ni olish uchun brauzerda ushbu manzilga kiring (TOKEN o'rniga
   o'z tokeningizni qo'ying):
   `https://api.telegram.org/botTOKEN/getUpdates`
   Javobda `"chat":{"id": ...}` qismidagi raqam — sizning Chat ID'ingiz.

## Ilovada sozlash

APK'ni telefonga o'rnatgach, ilovani oching, Bot Token va Chat ID'ni kiriting,
"Saqlash" tugmasini bosing va SMS ruxsatini bering. Shundan so'ng kelgan
barcha SMS'lar avtomatik Telegram'ga yuboriladi.

## Eslatma

- Ilova fonda ishlashi uchun ba'zi telefonlarda "Battery optimization"dan
  chiqarib qo'yish kerak bo'lishi mumkin (ayniqsa Xiaomi, Huawei kabi
  brendlarda).
- Android 8+ versiyalarda SMS_RECEIVED broadcast cheklovlardan ozod, shuning
  uchun qo'shimcha "foreground service" shart emas.
