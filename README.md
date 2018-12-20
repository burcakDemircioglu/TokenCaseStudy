# TokenCaseStudy
This simple android app is done as mini showcase of the Campaign QR(1.03) API's Payment Domain.
https://sandbox-developer.payosy.com/node/180

In the project 2 Async Tasks are running.
1. Getting QR For Sale
2. Payment

![Alt](/Screenshot2.png "Welcoming page")
![Alt](/Screenshot3.png "Got QR")
![Alt](/Screenshot1.png "Payment Successful")

As you can see from the screenshots above. There is a welcoming page with get Payment Info button.
When the user presses this button first asynctask runs and gets the QR from the API. Then this QR
is parsed and the info is extracted to show in the ui. This parsing is done although actually we have
the same info to get the QR but these tasks can be performed by different apps on different devices
so QR is parsed for the info even though it is not needed for this project.

After the info is shown a new pay button is shown to make it available to user to pay. After the user
clicks the pay button the second asynctask runs, the payment info is send to the API and a "successful"
message is shown to the user. But the app waits just for the request "OK" response but not for the
"terminal receives payment request" response and "terminal complete receipt" response because they are
out of the scope of this project.

As it is explained before these two tasks can be done by different devices (first one can be done by
a POS device or 1000TR device, second one from a phone or tablet), therefore the QR has to be transported
form one to another with an efficient and non physical (in the scope of the project it is said that
the tablet can not be used to read the QR by camera or any other way, the 1000TR is out of the tank and
the driver can not leave the tank to read the QR with the tablet) way. As long as both device include
bluetooth support, this transportation can be done by bluetooth connection by pairing the 1000TR device
with the tank's tablet. As long as bluetooth range is not limited with the 5-10 meters the pairing
shouldn't be a problem supposedly.

For more info on bluetooth ranges: (https://blog.nordicsemi.com/getconnected/things-you-should-know-about-bluetooth-range)