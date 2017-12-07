package com.fss.tmb;

public class RsaEncryption {

	private final static BigInteger one = new BigInteger("1");

	private BigInteger privateKey;

	private BigInteger publicKey;

	private BigInteger modulus;
        
        public String cipherText;

	RsaEncryption(String pin) {  		
                String modulus = "";
		String publicKey = "65537";
		  if(StaticStore.index == 113 || StaticStore.index == 114 || StaticStore.index == 115 || StaticStore.index == 138
				  || (StaticStore.index == 220 && StaticStore.tagType.equals("APP4"))
				  || (StaticStore.index == 220 && StaticStore.tagType.equals("APP2"))
				  ||(StaticStore.index == 220 && StaticStore.tagType.equals("APOV"))
				  || StaticStore.index == 216){                                
                  
                	modulus = StaticStore.defaultPublicKey;
                 }else{                    
                    modulus = StaticStore.publicKey;
                }		
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


}
