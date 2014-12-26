# -*- mode: ruby -*-
# vi: set ft=ruby :
# This Vagrant file helps the development of the cookbook.

Vagrant.configure("2") do |config|

  config.berkshelf.enabled = true
  config.vm.hostname = "cp-userservice"

  config.vm.synced_folder "./sql", "/vagrant_sql"
  config.vm.synced_folder "./target", "/vagrant_dist"
  config.vm.synced_folder "./target", "/vagrant_build"

  config.vm.box_url = "https://opscode-vm.s3.amazonaws.com/vagrant/opscode_centos-6.4_chef-11.4.4.box"
  config.vm.box = "platform-centos"
  config.vm.network :forwarded_port, guest: 3306, host: 3306  # MySQL
  config.vm.network :forwarded_port, guest: 9001, host: 9001  # Web app

  config.vm.provision :chef_solo do |chef|

    chef.log_level = :debug
    chef.json = {
      :mysql => {
        :server_root_password => 'cpmysql12',
        :server_debian_password => 'cpmysql12',
        :server_repl_password => 'cpmysql12',
        :allow_remote_root => true,
        :bind_address => '0.0.0.0'
      },
      :cp_mysql => {
        :run_script => '/vagrant_sql/createdb.sql'
      },
      cp_liquibase: {
             classpath_path: "/liquibase",
             driver_file_name: "connector-java-5.1.18.jar",
             zip_foldername: "userservice-liquibase",
             dist_dir: "/opt/liquibase",
             dist_local_file_path: "/vagrant_build/distributions/userservice-liquibase.zip",
             use_local_dist: "true"
     },
     database:  {
             driver_class: 'com.mysql.jdbc.Driver',
             jdbc_url: 'jdbc:mysql://localhost:3306/userservice',
             username: 'root',
             password: 'cpmysql12'
     }
    }
    chef.add_recipe "cp_baseconfiguration::default"
    chef.add_recipe "cp_java::default"
    chef.add_recipe "cp_mysql::default"
    chef.add_recipe "cp_liquibase::default"
  end
end
