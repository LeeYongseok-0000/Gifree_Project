package com.gifree.security.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.gifree.dto.MemberDTO;
import com.gifree.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter{
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{

         // Preflight요청은 체크하지 않음
         // 로그인을 처리하는 경로,  첨부파일 이미지를 사용하는 경로 등에 대해서는 체크하지 않도록 지정
         // Prefight로 전송되는 OPTIONS 방식을 체크하지 않는다. 
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }

        String path = request.getRequestURI();

        log.info("check uri......................."+path);

          //api/member/ 경로의 호출은 체크하지 않음 
    if(path.startsWith("/api/member/")) {
        return true;
      }

        // 이미지 조회 경로는 체크하지 않는다면
        if(path.startsWith("/api/products/view/")){
            return true;
        }
        if(path.startsWith("/api/events/")){
          return true;
      }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
    throws ServletException, IOException{
 log.info("------------------------JWTCheckFilter------------------");

    String authHeaderStr = request.getHeader("Authorization");

    try {
      //Bearer accestoken...
      String accessToken = authHeaderStr.substring(7);
      Map<String, Object> claims = JWTUtil.validateToken(accessToken);

      log.info("JWT claims: " + claims);

      //filterChain.doFilter(request, response); 
String email = (String) claims.get("email");
      String pw = (String) claims.get("pw");
      String nickname = (String) claims.get("nickname");
      Boolean social = (Boolean) claims.get("social");
      List<String> roleNames = (List<String>) claims.get("roleNames");

      MemberDTO memberDTO = new MemberDTO(email, pw, nickname, social.booleanValue(), roleNames);

      log.info("-----------------------------------");
      log.info(memberDTO);
      log.info(memberDTO.getAuthorities());

      UsernamePasswordAuthenticationToken authenticationToken
      = new UsernamePasswordAuthenticationToken(memberDTO, pw, memberDTO.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authenticationToken);

      filterChain.doFilter(request, response);

    }catch(Exception e){

      log.error("JWT Check Error..............");
      log.error(e.getMessage());

      Gson gson = new Gson();
      String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

      response.setContentType("application/json");
      PrintWriter printWriter = response.getWriter();
      printWriter.println(msg);
      printWriter.close();

    }
  }

}