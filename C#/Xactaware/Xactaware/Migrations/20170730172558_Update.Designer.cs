using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Xactaware.Models;

namespace Xactaware.Migrations
{
    [DbContext(typeof(XactawareContext))]
    [Migration("20170730172558_Update")]
    partial class Update
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
            modelBuilder
                .HasAnnotation("ProductVersion", "1.1.2")
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("Xactaware.Models.Address", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Address0");

                    b.Property<string>("Address1");

                    b.Property<string>("Address2");

                    b.Property<string>("Address3");

                    b.Property<string>("Postcode");

                    b.Property<string>("landline");

                    b.HasKey("Id");

                    b.ToTable("Address");
                });

            modelBuilder.Entity("Xactaware.Models.Role", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Rolename");

                    b.HasKey("Id");

                    b.ToTable("Role");
                });

            modelBuilder.Entity("Xactaware.Models.User", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Email");

                    b.Property<string>("Mobile");

                    b.Property<string>("Password");

                    b.Property<int?>("UseraddressId");

                    b.Property<string>("Username");

                    b.Property<int?>("UserroleId");

                    b.HasKey("Id");

                    b.HasIndex("UseraddressId");

                    b.HasIndex("UserroleId");

                    b.ToTable("User");
                });

            modelBuilder.Entity("Xactaware.Models.User", b =>
                {
                    b.HasOne("Xactaware.Models.Address", "Useraddress")
                        .WithMany()
                        .HasForeignKey("UseraddressId");

                    b.HasOne("Xactaware.Models.Role", "Userrole")
                        .WithMany()
                        .HasForeignKey("UserroleId");
                });
        }
    }
}
