localPort="1092"
privateKey="$HOME/.ssh/socks_rsa"
clientKey="$HOME/.ssh/id_rsa"

mkdir -p $(dirname $privateKey)
ssh-keygen -f ${privateKey} -t rsa -N '' -C '' > /dev/null 2>&1
ssh-keygen -f ${clientKey} -t rsa -N '' -C '' > /dev/null 2>&1

cat ${clientKey}.pub > $HOME/.ssh/authorized_keys

cat << EOF > $HOME/.ssh/sshd_config
HostKey ${privateKey}
AuthorizedKeysFile ${HOME}/.ssh/authorized_keys
UsePrivilegeSeparation no
TCPKeepAlive yes
Subsystem sftp /usr/lib/openssh/sftp-server
EOF

bannerFile="/app/.ssh/banner.txt"
echo "Welcome!" > ${bannerFile}

echo "Starting sshd on localhost:${localPort}..."
/usr/sbin/sshd -f $HOME/.ssh/sshd_config -o "Port ${localPort}" -o "Banner ${bannerFile}"

echo "Starting Socks proxy on 9090"
ssh -o StrictHostKeyChecking=no -p ${localPort} -D 9090 localhost -N &