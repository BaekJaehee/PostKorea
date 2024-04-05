from django.db import models

# Create your models here.
class relic_list(models.Model):
    list_id = models.AutoField(primary_key=True)    # AutoField = auto_increment
    no = models.IntegerField(unique=True)
    ccma_name = models.TextField(null=True)
    ccba_mnm1 = models.TextField(null=True)
    ccba_mnm2 = models.TextField(null=True)
    ccba_kdcd = models.TextField(null=True)
    ccba_ctcd = models.TextField(null=True)
    ccba_asno = models.TextField(null=True)

    class Meta:
        db_table = 'relic_list'

class relic_detail(models.Model):
    relic_id = models.AutoField(primary_key=True, unique=True)
    list_id = models.ForeignKey(relic_list, on_delete=models.CASCADE, db_column='list_id')
    ccba_mnm1 = models.TextField(null=True)
    ccba_mnm2 = models.TextField(null=True)
    ccba_kdcd = models.TextField(null=True)
    ccba_asno = models.TextField(null=True)
    ccba_ctcd = models.TextField(null=True)
    ccba_cpno = models.TextField(null=True)
    longitude = models.TextField(null=True)
    latitude = models.TextField(null=True)
    ccma_name = models.TextField(null=True)
    gcode_name = models.TextField(null=True)
    bcode_name = models.TextField(null=True)
    mcode_name = models.TextField(null=True)
    scode_name = models.TextField(null=True)
    ccba_quan = models.TextField(null=True)
    ccba_asdt = models.TextField(null=True)
    ccba_lcad = models.TextField(null=True)
    ccce_name = models.TextField(null=True)
    ccba_poss = models.TextField(null=True)
    ccba_admin = models.TextField(null=True)
    image_url = models.TextField(null=True)
    content = models.TextField(null=True)
    region1 = models.TextField(null=True)
    region2 = models.TextField(null=True)

    class Meta:
        db_table = 'relic_detail'