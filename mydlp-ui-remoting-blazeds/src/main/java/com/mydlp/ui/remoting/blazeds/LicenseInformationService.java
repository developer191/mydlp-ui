package com.mydlp.ui.remoting.blazeds;

import org.springframework.security.access.annotation.Secured;

import com.mydlp.ui.domain.AuthSecurityRole;
import com.mydlp.ui.domain.LicenseInformation;

@Secured(AuthSecurityRole.ROLE_ADMIN)
public interface LicenseInformationService {
	
	public LicenseInformation getLicense();
	
	public LicenseInformation save(LicenseInformation l);
	
	public void remove(LicenseInformation l);
	
	public Boolean isSoftLimit();
	
	public Boolean isHardLimit();
	
}
