# JugsooMarketApp

The Challenge:

Your mission, should you choose to accept, is to showcase your amazing talents as an engineer by doing the following:

The owner of Jungsoo’s Market (a very famous client of ours) has asked us to build his store an android app to help improve their customer experience.

Customers want the ability to scan items using their phones as they walk around the store, and add them to a virtual basket. For each item added to the customer’s order, display the items name and price. In addition to allowing users to add and remove items from their basket, your app should also show a calculated total for all the items in the order.

Jungsoo’s Market will provide you with a database of the items in their inventory in the format shown at the end of this document. Each item in their store is identified using a QR code. Ideally, customers can use their phones camera to scan the QR code, and retrieve the items information, but at a minimum, users should have the ability to manually enter the items identifier to look the items up.

We recommend setting aside anywhere from 2-5 hours to complete this task, but that’s totally up to you. We prefer functionality over appearance, so if necessary, prioritize working code over something pretty (we can always make it look nice later).

Sample Data:

```
[
 {
   "id": "0001",
   "qrUrl": "https://zxing.org/w/chart?cht=qr&chs=350x350&chld=L&choe=UTF-8&chl=0001",
   "thumbnail": "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/apple/237/banana_1f34c.png",
   "name": "Banana",
   "price": "$1.00"
 },
 {
   "id": "0002",
   "qrUrl": "https://zxing.org/w/chart?cht=qr&chs=350x350&chld=L&choe=UTF-8&chl=0002",
   "thumbnail": "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/apple/237/red-apple_1f34e.png",
   "name": "Apple",
   "price": "$4.00"
 },
 {
   "id": "0003",
   "qrUrl": "https://zxing.org/w/chart?cht=qr&chs=350x350&chld=L&choe=UTF-8&chl=0003",
   "thumbnail": "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/apple/237/sparkles_2728.png",
   "name": "Other Stuff",
   "price": "$10.00"
 }
]
```
