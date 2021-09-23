import sbt.Keys.versionScheme

name := "zohocrmsdk-2-0"
scalaVersion := "2.13.5"
organization := "com.zoho.crm"
sonatypeProfileName := "com.zoho"
organizationName:= "zoho"
organizationHomepage := Some(url("https://www.zoho.com/crm/"))
description := "An API client for CRM customers, with which they can call ZOHO CRM APIs with ease"
sonatypeCredentialHost := "s01.oss.sonatype.org"
versionScheme := Some("semver-spec")
publishMavenStyle := true
pomIncludeRepository := { _ =>
  false
}
credentials += Credentials(
  realm    = "Sonatype Nexus Repository Manager",
  host     = "s01.oss.sonatype.org",
  userName = sys.env.getOrElse("OSSRH_USER", ""),
  passwd   = sys.env.getOrElse("OSSRH_PASSWORD", "")
)
publishTo := sonatypePublishToBundle.value
developers := List(
  Developer(
    id = "ZOHO CRM API TEAM",
    name = "ZOHO CRM API TEAM",
    email = "support@zohocrm.com",
    url = url("https://github.com/zoho/zohocrm-scala-sdk-2.0")
  )
)
crossPaths := false
pomExtra :=
  <url>https://github.com/zoho/zohocrm-scala-sdk-2.0</url>
    <scm>
      <url>https://github.com/zoho/zohocrm-scala-sdk-2.0</url>
      <connection>scm:git:git@github.com:zoho/zohocrm-scala-sdk-2.0</connection>
    </scm>
licenses := Seq(
  "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0")
)
libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.47",
  "org.json" % "json" % "20170516",
  "org.apache.httpcomponents" % "httpcore" % "4.4.6",
  "org.apache.httpcomponents" % "httpclient" % "4.5.3",
  "org.apache.httpcomponents" % "httpmime" % "4.5",
  "org.apache.commons" % "commons-io" % "1.3.2"
)