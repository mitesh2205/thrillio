$libPath = 'd:\Git Projects\bookmarking app\Thrill-io\Java\WebContent\WEB-INF\lib'

# Create lib directory if it doesn't exist
New-Item -ItemType Directory -Force -Path $libPath

# Download URLs
$urls = @{
    'mysql-connector' = 'https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar'
    'jstl' = 'https://repo1.maven.org/maven2/javax/servlet/jstl/1.2/jstl-1.2.jar'
    'commons-lang3' = 'https://repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.12.0/commons-lang3-3.12.0.jar'
    'jsoup' = 'https://repo1.maven.org/maven2/org/jsoup/jsoup/1.15.4/jsoup-1.15.4.jar'
}

# Download each JAR file
foreach ($name in $urls.Keys) {
    $url = $urls[$name]
    $fileName = Split-Path -Path $url -Leaf
    Write-Host "Downloading $fileName..."
    Invoke-WebRequest -Uri $url -OutFile (Join-Path $libPath $fileName)
}

Write-Host "All JAR files have been downloaded to $libPath"
