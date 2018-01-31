package com.awsSesUsingSDK.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface SendMailRawService {
	public void sendMailRaw() throws AddressException, MessagingException, IOException ;
}
