package com.mahmoud.compdfexample

import android.app.Application
import com.compdfkit.core.document.CPDFSdk

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        CPDFSdk.init(this,
                "SliOQG4TMl4ugP5+Wk6r5sun9j3WU9PfXzUxvtK+Rv4VqfREBvFpsokeoTJXxkpw5fZbkwaG25orNwQ2ZBG9xjRXbyyeuOyDhsTZ/+9t43hCgy3Km3g6YoE+SDZrLBHLwFpwLjg85iV5f/uAUy1YFewfEp949wsodecX/fMPSKlvPTHCxJMSXZszJMZQKy62dVuScQdzBNy2Ew4/fQJnbDLc54Tsv5Lq/KwWBSdKwDoEbuHQ+KseZMYCfW3i6b7EMO15FDqrsEiKuSGh7hxanB5Uh2Uv09sxhXLznuHt8fHgcBTzBfEuE2fAqQTYZnulZ0HR3svKfAT+cLy689joM68kD+Q9C95FZo3W7DLPF5KOumuA9Mk+OaEvZYH9jCTlpKC+YY6C87eehfjV9+dpD73un93BkGFbpQ16S3Gr3iIVMTJpAcl9mjM4GruTN63aXxMCeZuIRbq0gWTv3E1F+w2g+uKHp2YAmLF/pXYTXJLvPocoYjPv3BlY4Ie+tovu9sJuLTkvUH14KdSeOPJI6T0UCQV2l+Zvykq1AAp+LACVeCrQZggTml/lXlaXxIPSZ06k/LIiIfVGRMg/YofFAR5ezGr87UQdLLohm/oRtekmKpcPydl2DnlFmlAOfIib4vQVvGSvm2JyN2P2RUqvqdEFluqEmixMiwUIyYDY+R0DjkS1Q1UYC+Rs+dT6KdXYP50ms0YtnB/OPQNU/24yCLydXB0kjQBStl6CKs4egh7hGytevJUZXlTecoXwVN4RW8/aOp1pGeFKODOr/fsgKQ==");
    }
}