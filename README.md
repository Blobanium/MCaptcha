# MCaptcha
Captcha Plugin For Minecraft

This was technically a plugin derived from the bumble kingdom server project, avalible to use.

NOTE: This README Is A WIP, use this plugin with caution!

## Getting Started
**You are going to need a HCaptcha Account to setup the plugin, do that if you havent already**

### 1. Set up a new Hcaptcha SiteKey

#### If you already have an existing hCaptcha account
Under `Sites`, in your hCaptcha Profile, click on `New Site`, a menu should appear, Make sure your captcha behavior is set to `Always Challenge` otherwise this will not work properly. Once complete, click "Save". Copy the Sitekey into the MCaptcha config under `hcaptcha_sitekey`

#### If you made a new hCaptcha account or need to make one.
Make a hCaptcha Account if you havent already. A Sitekey should be already generated. You can either follow the instructions if you had an existing account, but do not create a new sitekey. Use the existing sitekey that has been generated for you. You can also go to the [hCaptcha documentation](https://docs.hcaptcha.com/) and find it there, it is also where you can find your secret.

### 2. Configuration File
Next you want to go to your configuration file, you will find 4 options all of them are required.

`server_ip`: This is typically the server ip you send out to your users for them to join on your server.

`port`: This is the port that the captcha webserver will be hosted on your server. 

`hcaptcha_sitekey`: The hCaptcha Sitekey, we went over this in step 1 on how you can find your sitekey.

`hcaptcha_secret`: The hCaptcha secret. This is found on your profile settings. You can also find this on the hCaptcha documentation as well.



Now once all of that is done try to captcha someone on your server by doing `/captcha <user>`

## The Captcha Process
Once the captcha command has been sent, they will be shown a message saying "Captcha Required" and the player will be frozen. They must click on a link sent in their chat (in the form of a message). This will open the Captcha Challenge Page that is being hosted on your webserver. They must pass the captcha challenge before clicking submit.
