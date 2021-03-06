# -*- mode: ruby -*-
# vi: set ft=ruby :
# This Vagrant file helps the development of the cookbook.

Vagrant.configure("2") do |config|

  config.berkshelf.enabled = true
  config.vm.hostname = "cp-userservice"

  config.vm.synced_folder "./sql", "/vagrant_sql"
  config.vm.synced_folder "./target", "/vagrant_dist"
  config.vm.synced_folder "./target", "/vagrant_build"

  config.vm.box_url = "http://opscode-vm-bento.s3.amazonaws.com/vagrant/virtualbox/opscode_centos-6.5_chef-provisionerless.box"
  config.vm.box = "platform2-centos"
  config.vm.network :forwarded_port, guest: 3306, host: 3307 # MySQL
  config.vm.network :forwarded_port, guest: 9000, host: 10001  # Web app
  config.vm.network :forwarded_port, guest: 22, host: 1322

  config.vm.provision :chef_solo do |chef|

    chef.log_level = :debug
    chef.json = {
      :mysql => {
        :server_root_password => 'cpmysql123',
        :server_debian_password => 'cpmysql123',
        :server_repl_password => 'cpmysql123',
        :allow_remote_root => true,
        :bind_address => '0.0.0.0'
      },
      :cp_mysql => {
        :run_script => '/vagrant_sql/createdb.sql'
      },
      cp_liquibase: {
             classpath_path: "/liquibase",
             driver_file_name: "connector-java-5.1.18.jar",
             zip_foldername: "cp-liquibase",
             dist_dir: "/opt/liquibase",
             dist_local_file_path: "/vagrant_build/distributions/cp-liquibase.zip",
             use_local_dist: "true"
     },
     database:  {
             driver_class: 'com.mysql.jdbc.Driver',
             jdbc_url: 'jdbc:mysql://localhost:3306/userservice',
             username: 'root',
             password: 'cpmysql123'
     }
    }
    chef.add_recipe "cp_baseconfiguration::default"
    chef.add_recipe "cp_java::default"
    chef.add_recipe "cp_mysql::default"
    chef.add_recipe "cp_liquibase::default"
  end
end
