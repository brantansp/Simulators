package com.fss.tmb;

public class RsaEncryptionNew {
	private final static BigInteger one = new BigInteger("1");

	private BigInteger privateKey;

	private BigInteger publicKey;

	private BigInteger modulus;
        
        public String cipherText;

        public RsaEncryptionNew(String pin,String modulus) {            
                //String modulus = "";
		String publicKey = "65537";
             /*   if(StaticStore.index == 113 || StaticStore.index == 114){                   
                    modulus = StaticStore.defaultPublicKey;
                }else{                    
                    modulus = StaticStore.publicKey;
                }		*/
		BigInteger encrypted = encrypt(new BigInteger(pin),
				new BigInteger(publicKey), new BigInteger(modulus));		
                cipherText = encrypted+"";
	}

	public BigInteger getPublicKey() {
		return this.publicKey;
	}

	public BigInteger getPrivateKey() {
		return this.privateKey;
	}

	public BigInteger getModulus() {
		return this.modulus;
	}

	public BigInteger encrypt(BigInteger message, BigInteger publicKey,
			BigInteger modulus) {
		return message.modPow(publicKey, modulus);
	}

	public static void main(String[] args) {
		RsaEncryptionNew newEnc = new RsaEncryptionNew("1234","203577465141885203944391850079714410739");
		//RSADecryptionNEW dec=new RSADecryptionNEW();
		 StaticStore.LogPrinter('i',"Encrypted Value : " + newEnc.cipherText);
}
}
