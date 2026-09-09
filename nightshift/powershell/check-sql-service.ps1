$services = Get-Service | Where-Object {
    $_.Name -like "MSSQL*"
}

if (-not $services) {
    Write-Host "No SQL Server service found."
    exit 1
}

$services |
        Select-Object Name, Status, DisplayName |
        Format-Table -AutoSize