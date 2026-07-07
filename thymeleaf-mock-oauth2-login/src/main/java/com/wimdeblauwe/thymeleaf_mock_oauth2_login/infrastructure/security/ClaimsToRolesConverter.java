package com.wimdeblauwe.thymeleaf_mock_oauth2_login.infrastructure.security;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.util.Assert;

public class ClaimsToRolesConverter {

  private static final String REALM_ACCESS = "realm_access";
  private static final String RESOURCE_ACCESS = "resource_access";
  private static final String ROLES = "roles";
  private static final String ROLE_PREFIX = "ROLE_";

  public static Set<GrantedAuthority> convert(ClaimAccessor claimAccessor) {

    String authorizedParty = claimAccessor.getClaim("azp");
    Assert.notNull(authorizedParty, "Could not find the azp claim in the token");

    Set<GrantedAuthority> authorities = new HashSet<>();
    Map<String, Object> realmAccess = claimAccessor.getClaimAsMap(REALM_ACCESS);
    if (realmAccess != null) {
      @SuppressWarnings("unchecked")
      List<String> roles = (List<String>) realmAccess.get(ROLES);
      if (roles != null) {
        authorities.addAll(
            roles.stream().map(rn -> new SimpleGrantedAuthority(ROLE_PREFIX + rn)).toList());
      }
    }

    Map<String, Object> resourceAccess = claimAccessor.getClaimAsMap(RESOURCE_ACCESS);
    if (resourceAccess != null) {
      Object appResource = resourceAccess.get(authorizedParty);
      if (appResource instanceof Map) {
        @SuppressWarnings("unchecked")
        Map<String, Object> resourceMap = (Map<String, Object>) appResource;
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) resourceMap.get(ROLES);
        if (roles != null) {
          authorities.addAll(
              roles.stream().map(rn -> new SimpleGrantedAuthority(ROLE_PREFIX + rn)).toList());
        }
      }
    }
    return authorities;
  }

  private ClaimsToRolesConverter() {}
}
